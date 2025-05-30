package com.NamVu.ReviewSpring.service.impl;

import com.NamVu.ReviewSpring.dto.request.UserCreateRequest;
import com.NamVu.ReviewSpring.dto.request.UserUpdateRequest;
import com.NamVu.ReviewSpring.dto.response.PageResponse;
import com.NamVu.ReviewSpring.dto.response.UserDetailResponse;
import com.NamVu.ReviewSpring.enums.Gender;
import com.NamVu.ReviewSpring.enums.UserStatus;
import com.NamVu.ReviewSpring.exception.AppException;
import com.NamVu.ReviewSpring.exception.ErrorCode;
import com.NamVu.ReviewSpring.mapper.AddressMapper;
import com.NamVu.ReviewSpring.mapper.UserMapper;
import com.NamVu.ReviewSpring.model.Address;
import com.NamVu.ReviewSpring.model.User;
import com.NamVu.ReviewSpring.repository.SearchRepository;
import com.NamVu.ReviewSpring.repository.UserRepository;
import com.NamVu.ReviewSpring.service.UserService;
import com.NamVu.ReviewSpring.specification.UserSpec;
import com.NamVu.ReviewSpring.specification.UserSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final PasswordEncoder passwordEncoder;
    private final SearchRepository searchRepository;

    @Override
    public long saveUser(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.mapToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Address> addresses = request.getAddresses().stream()
                .map(addressRequest -> {
                    Address address = addressMapper.mapToEntity(addressRequest);
                    address.setUser(user);
                    return address;
                })
                .collect(Collectors.toSet());
        user.setAddresses(addresses);

        userRepository.save(user);

        log.info("User saved! userId={}", user.getId());

        return user.getId();
    }

    @Override
    public void updateUser(long userId, UserUpdateRequest request) {
        User user = getUserById(userId);

        userMapper.updatedUser(user, request);

        Set<Address> addresses = request.getAddresses().stream()
                .map(addressRequest -> {
                    Address address = addressMapper.mapToEntity(addressRequest);
                    address.setUser(user);
                    return address;
                })
                .collect(Collectors.toSet());
        user.getAddresses().clear();
        user.getAddresses().addAll(addresses);

        userRepository.save(user);

        log.info("User updated! userId={}", userId);
    }

    @Override
    public void changeStatus(long userId, UserStatus status) {
        User user = getUserById(userId);
        user.setStatus(status);
        userRepository.save(user);

        log.info("User changed status! userId={}", userId);
    }

    @Override
    @Transactional
    public void deleteUser(Set<Long> userIds) {
        userRepository.deleteByIdIn(userIds);

        log.info("User deleted! userId={}", userIds);
    }

    @Override
    public UserDetailResponse getUser(long userId) {
        User user = getUserById(userId);
        return userMapper.mapToDetailResponse(user);
    }

    @Override
    public PageResponse<UserDetailResponse> getAllUser(int pageNo, int pageSize,
                                                       String sortBy, String direction) {
        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> users = userRepository.findAll(pageable);

        return PageResponse.<UserDetailResponse>builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(users.getTotalPages())
                .totalElement(users.getTotalElements())
                .data(users.stream()
                        .map(userMapper::mapToDetailResponse)
                        .toList())
                .build();
    }

    @Override
    public PageResponse<?> searchWithSqlQuery(String search, int pageNo, int pageSize,
                                                               String sortBy, String direction) {
        return searchRepository.search(search, pageNo, pageSize, sortBy, direction);
    }

    @Override
    public PageResponse<?> advanceSearchWithCriteria(int pageNo, int pageSize, String sortBy, String address, String... search) {
        return searchRepository.searchUserByCriteria(pageNo, pageSize, sortBy, address, search);
    }

    @Override
    public PageResponse<User> advanceSearchWithSpecification(Pageable pageable, String[] user, String[] address) {
        Page<User> users = null;

        List<User> userList = new ArrayList<>();
        if (user != null && address != null)  {
            // tim kiem tren ca user va address => join table
        } else if (user != null) {
            // tim kiem tren user
//            Specification<User> spec = UserSpec.hasLastName(user[0])
//                    .and(UserSpec.hasFirstName(user[1]))
//                    .and(UserSpec.notEqualGender(Gender.MALE));
//
//            userList = userRepository.findAll(spec);

            UserSpecificationBuilder builder = new UserSpecificationBuilder();

            for (String s : user) {
                Pattern pattern = Pattern.compile("(\\w+?)([<:>~!])(.*)(\\p{Punct}?)(\\p{Punct}?)");
                Matcher matcher = pattern.matcher(s);
                if (matcher.find()) {
                    builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5));
                }
            }

            userList = userRepository.findAll(builder.build());
        } else {
            userList = userRepository.findAll();
        }

        return PageResponse.<User>builder()
                .pageNo(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .totalPage(10)
                .totalElement(10L)
                .data(userList)
                .build();
    }

    private User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }
}
