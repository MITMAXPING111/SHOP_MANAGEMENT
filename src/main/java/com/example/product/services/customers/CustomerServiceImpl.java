package com.example.product.services.customers;

import com.example.product.entities.users.*;
import com.example.product.models.request.users.ReqCustomerDTO;
import com.example.product.models.request.users.roles.ReqRoleId;
import com.example.product.models.response.users.ResCustomerDTO;
import com.example.product.models.response.users.roles.ResRole;
import com.example.product.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleRepo roleRepo;
    private final AddressRepository addressRepository;
    private final AccountImageRepository accountImageRepository;

    @Override
    @Transactional
    public ResCustomerDTO createCustomer(ReqCustomerDTO reqCustomerDTO) {
        Customer customer = mapToEntity(reqCustomerDTO);
        customer.setCreatedAt(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);
        return mapToDTO(savedCustomer);
    }

    @Override
    @Transactional
    public ResCustomerDTO updateCustomer(Long id, ReqCustomerDTO reqCustomerDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + id));

        BeanUtils.copyProperties(reqCustomerDTO, customer, "id", "createdAt", "createdBy", "roles", "addresses",
                "accountImage");

        customer.setUpdatedAt(LocalDateTime.now());
        customer.setUpdatedBy(reqCustomerDTO.getUpdatedBy());

        // Update roles
        Set<Role> roles = new HashSet<>();
        if (reqCustomerDTO.getReqRoleIds() != null) {
            for (ReqRoleId roleId : reqCustomerDTO.getReqRoleIds()) {
                Role role = roleRepo.findById(roleId.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + roleId.getId()));
                roles.add(role);
            }
        }
        customer.setRoles(roles);

        // Update addresses
        Set<Address> addresses = new HashSet<>();
        if (reqCustomerDTO.getAddressIds() != null) {
            addresses = addressRepository.findAllById(reqCustomerDTO.getAddressIds()).stream()
                    .collect(Collectors.toSet());
        }
        customer.setAddresses(addresses);

        // Update account image
        AccountImage currentAccountImage = customer.getAccountImage();
        if (reqCustomerDTO.getAccountImageId() != null) {
            AccountImage accountImage = accountImageRepository.findById(reqCustomerDTO.getAccountImageId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "AccountImage not found with ID: " + reqCustomerDTO.getAccountImageId()));

            // Check if the AccountImage is already assigned to another Customer
            if (accountImage.getCustomer() != null && !accountImage.getCustomer().getId().equals(customer.getId())) {
                throw new IllegalStateException("AccountImage with ID " + reqCustomerDTO.getAccountImageId()
                        + " is already assigned to another customer.");
            }

            // Clear existing AccountImage if different
            if (currentAccountImage != null && !currentAccountImage.getId().equals(accountImage.getId())) {
                currentAccountImage.setCustomer(null);
                accountImageRepository.save(currentAccountImage);
            }

            // Set the new AccountImage
            customer.setAccountImage(accountImage);
            accountImage.setCustomer(customer);
            accountImageRepository.save(accountImage);
        } else if (reqCustomerDTO.getAccountImageData() != null) {
            // Create new AccountImage if data is provided
            AccountImage accountImage = new AccountImage();
            accountImage.setUrl_image(reqCustomerDTO.getAccountImageData().getUrl_image() != null
                    ? reqCustomerDTO.getAccountImageData().getUrl_image()
                    : "default_image_url");
            accountImage.setId_image(reqCustomerDTO.getAccountImageData().getId_image());
            accountImage.setId_folder(reqCustomerDTO.getAccountImageData().getId_folder());
            accountImage.setCustomer(customer);
            customer.setAccountImage(accountImage);
            accountImageRepository.save(accountImage);
        } else {
            // Clear existing AccountImage if no ID or data is provided
            if (currentAccountImage != null) {
                currentAccountImage.setCustomer(null);
                accountImageRepository.save(currentAccountImage);
                customer.setAccountImage(null);
            }
        }

        Customer updatedCustomer = customerRepository.save(customer);
        return mapToDTO(updatedCustomer);
    }

    @Override
    public ResCustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + id));
        return mapToDTO(customer);
    }

    @Override
    public List<ResCustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with ID: " + id);
        }
        customerRepository.deleteById(id);
    }

    @Override
    public Customer handleGetCustomerByUsername(String username) {
        return this.customerRepository.findByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + username));
    }

    private Customer mapToEntity(ReqCustomerDTO dto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(dto, customer, "id", "reqRoleIds", "addressIds", "accountImageId", "accountImageData");

        // Map roles
        Set<Role> roles = new HashSet<>();
        if (dto.getReqRoleIds() != null) {
            for (ReqRoleId roleId : dto.getReqRoleIds()) {
                Role role = roleRepo.findById(roleId.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Role not found with ID: " + roleId.getId()));
                roles.add(role);
            }
        }
        customer.setRoles(roles);

        // Map addresses
        Set<Address> addresses = new HashSet<>();
        if (dto.getAddressIds() != null) {
            addresses = addressRepository.findAllById(dto.getAddressIds()).stream().collect(Collectors.toSet());
        }
        customer.setAddresses(addresses);

        // Map account image
        if (dto.getAccountImageId() != null) {
            AccountImage accountImage = accountImageRepository.findById(dto.getAccountImageId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "AccountImage not found with ID: " + dto.getAccountImageId()));
            if (accountImage.getCustomer() != null) {
                throw new IllegalStateException("AccountImage with ID " + dto.getAccountImageId()
                        + " is already assigned to another customer.");
            }
            customer.setAccountImage(accountImage);
            accountImage.setCustomer(customer);
            accountImageRepository.save(accountImage);
        } else if (dto.getAccountImageData() != null) {
            // Create new AccountImage if data is provided
            AccountImage accountImage = new AccountImage();
            accountImage.setUrl_image(dto.getAccountImageData().getUrl_image() != null
                    ? dto.getAccountImageData().getUrl_image()
                    : "default_image_url");
            accountImage.setId_image(dto.getAccountImageData().getId_image());
            accountImage.setId_folder(dto.getAccountImageData().getId_folder());
            accountImage.setCustomer(customer);
            customer.setAccountImage(accountImage);
            accountImageRepository.save(accountImage);
        } else {
            // Create default AccountImage if neither ID nor data is provided
            AccountImage accountImage = new AccountImage();
            accountImage.setUrl_image("default_image_url");
            accountImage.setId_image("default_image_id");
            accountImage.setId_folder("default_folder_id");
            accountImage.setCustomer(customer);
            customer.setAccountImage(accountImage);
            accountImageRepository.save(accountImage);
        }

        return customer;
    }

    public boolean isEmailExist(String email) {
        return this.customerRepository.existsByEmail(email);
    }

    private ResCustomerDTO mapToDTO(Customer customer) {
        Set<ResRole> resRoles = customer.getRoles().stream()
                .map(role -> {
                    ResRole resRole = new ResRole();
                    resRole.setId(role.getId());
                    resRole.setName(role.getName());
                    return resRole;
                })
                .collect(Collectors.toSet());

        return ResCustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .avatar(customer.getAvatar())
                .gender(customer.getGender())
                .enabled(customer.isEnabled())
                .createBy(customer.getCreatedBy())
                .createAt(customer.getCreatedAt())
                .updateBy(customer.getUpdatedBy())
                .updateAt(customer.getUpdatedAt())
                .resRoles(resRoles)
                .addressIds(customer.getAddresses() != null
                        ? customer.getAddresses().stream().map(Address::getId).collect(Collectors.toSet())
                        : null)
                .accountImageId(customer.getAccountImage() != null ? customer.getAccountImage().getId() : null)
                .build();
    }
}