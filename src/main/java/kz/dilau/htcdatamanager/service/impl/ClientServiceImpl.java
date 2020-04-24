package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.Client;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.ClientRepository;
import kz.dilau.htcdatamanager.service.ClientService;
import kz.dilau.htcdatamanager.web.dto.ClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public List<ClientDto> getAll(String token) {
        return Collections.emptyList();
    }

    @Override
    public Long save(String token, ClientDto dto) {
        return saveClient(new Client(), dto);
    }

    public Long saveClient(Client client, ClientDto dto) {
        client.setFirstName(dto.getFirstName());
        client.setSurname(dto.getSurname());
        client.setPatronymic(dto.getPatronymic());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setEmail(dto.getEmail());
        client.setGender(dto.getGender());
        return clientRepository.save(client).getId();
    }

    @Override
    public void update(String token, Long id, ClientDto dto) {
        Client client = getClientById(id);
        saveClient(client, dto);
    }

    @Override
    public void deleteById(String token, Long id) {
        Client client = getClientById(id);
        client.setRemoved(true);
        clientRepository.save(client);
    }

    public Client getClientById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            if (optionalClient.get().isRemoved()) {
                throw EntityRemovedException.createClientRemovedById(id);
            }
            return optionalClient.get();
        } else {
            throw NotFoundException.createClientNotFoundById(id);
        }
    }
}
