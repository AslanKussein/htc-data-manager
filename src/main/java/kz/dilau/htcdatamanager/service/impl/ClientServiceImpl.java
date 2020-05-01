package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Client;
import kz.dilau.htcdatamanager.domain.enums.Gender;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.ClientRepository;
import kz.dilau.htcdatamanager.service.ClientService;
import kz.dilau.htcdatamanager.web.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Override
    public ClientDto findClientByPhoneNumber(String phoneNumber) {
        return clientRepository
                .findByPhoneNumber(phoneNumber)
                .map(ClientDto::new)
                .orElse(null);
    }

    @Override
    public ClientDto getById(String token, Long id) {
        Client client = getClientById(id);
        return new ClientDto(client);
    }

    @Override
    public ClientDto save(ClientDto dto) {
        return saveClient(new Client(), dto);
    }

    private ClientDto saveClient(Client client, ClientDto dto) {
        client.setFirstName(dto.getFirstName());
        client.setSurname(dto.getSurname());
        client.setPatronymic(dto.getPatronymic());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setEmail(dto.getEmail());
        client.setGender(nonNull(dto.getGender()) ? dto.getGender() : Gender.UNKNOWN);
        client = clientRepository.save(client);
        return new ClientDto(client);
    }

    @Override
    public ClientDto update(String token, Long id, ClientDto dto) {
        Client client = getClientById(id);
        return saveClient(client, dto);
    }

    @Override
    public ClientDto deleteById(String token, Long id) {
        Client client = getClientById(id);
        client.setIsRemoved(true);
        client = clientRepository.save(client);
        return new ClientDto(client);
    }

    @Override
    public Client getClientById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            if (optionalClient.get().getIsRemoved()) {
                throw EntityRemovedException.createClientRemovedById(id);
            }
            return optionalClient.get();
        } else {
            throw NotFoundException.createClientNotFoundById(id);
        }
    }
}
