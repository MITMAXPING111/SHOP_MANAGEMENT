package com.example.product.services.AccountImage;

import com.example.product.entities.users.AccountImage;
import com.example.product.entities.users.Customer;
import com.example.product.entities.users.User;
import com.example.product.models.request.users.ReqAccountImageDTO;
import com.example.product.models.response.users.ResAccountImageDTO;
import com.example.product.repositories.AccountImageRepository;
import com.example.product.repositories.CustomerRepository;
import com.example.product.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountImageServiceImpl implements AccountImageService {

    private final AccountImageRepository accountImageRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    @Override
    public ResAccountImageDTO createAccountImage(ReqAccountImageDTO reqAccountImageDTO) {
        AccountImage accountImage = mapToEntity(reqAccountImageDTO);

        // Set default created/updated time if not provided
        accountImage.setCreatedAt(
                reqAccountImageDTO.getCreatedAt() != null ? reqAccountImageDTO.getCreatedAt() : LocalDateTime.now());

        AccountImage saved = accountImageRepository.save(accountImage);
        return mapToDTO(saved);
    }

    @Override
    public ResAccountImageDTO updateAccountImage(Long id, ReqAccountImageDTO reqAccountImageDTO) {
        AccountImage existing = accountImageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AccountImage not found with ID: " + id));

        // Update primitive fields
        existing.setUrl_image(reqAccountImageDTO.getUrlImage());
        existing.setId_image(reqAccountImageDTO.getIdImage());
        existing.setId_folder(reqAccountImageDTO.getIdFolder());
        existing.setUpdatedBy(reqAccountImageDTO.getUpdatedBy());
        existing.setUpdatedAt(LocalDateTime.now());

        // Update associated user
        if (reqAccountImageDTO.getUserId() != null) {
            User user = userRepository.findById(reqAccountImageDTO.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "User not found with ID: " + reqAccountImageDTO.getUserId()));
            existing.setUser(user);
        } else {
            existing.setUser(null);
        }

        // Update associated customer
        if (reqAccountImageDTO.getCustomerId() != null) {
            Customer customer = customerRepository.findById(reqAccountImageDTO.getCustomerId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Customer not found with ID: " + reqAccountImageDTO.getCustomerId()));
            existing.setCustomer(customer);
        } else {
            existing.setCustomer(null);
        }

        AccountImage updated = accountImageRepository.save(existing);
        return mapToDTO(updated);
    }

    @Override
    public void deleteAccountImage(Long id) {
        if (!accountImageRepository.existsById(id)) {
            throw new EntityNotFoundException("AccountImage not found with ID: " + id);
        }
        accountImageRepository.deleteById(id);
    }

    @Override
    public ResAccountImageDTO getAccountImageById(Long id) {
        AccountImage accountImage = accountImageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AccountImage not found with ID: " + id));
        return mapToDTO(accountImage);
    }

    @Override
    public List<ResAccountImageDTO> getAllAccountImages() {
        return accountImageRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Map DTO -> Entity
    private AccountImage mapToEntity(ReqAccountImageDTO dto) {
        AccountImage accountImage = new AccountImage();

        accountImage.setUrl_image(dto.getUrlImage());
        accountImage.setId_image(dto.getIdImage());
        accountImage.setId_folder(dto.getIdFolder());

        accountImage.setCreatedBy(dto.getCreatedBy());
        accountImage.setUpdatedBy(dto.getUpdatedBy());

        // Set User
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + dto.getUserId()));
            accountImage.setUser(user);
        }

        // Set Customer
        if (dto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(
                            () -> new EntityNotFoundException("Customer not found with ID: " + dto.getCustomerId()));
            accountImage.setCustomer(customer);
        }

        return accountImage;
    }

    // Map Entity -> DTO
    private ResAccountImageDTO mapToDTO(AccountImage entity) {
        ResAccountImageDTO dto = new ResAccountImageDTO();

        dto.setId(entity.getId());
        dto.setUrlImage(entity.getUrl_image());
        dto.setIdImage(entity.getId_image());
        dto.setIdFolder(entity.getId_folder());

        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());

        if (entity.getUser() != null) {
            dto.setUserId(entity.getUser().getId());
        }

        if (entity.getCustomer() != null) {
            dto.setCustomerId(entity.getCustomer().getId());
        }

        return dto;
    }
}
