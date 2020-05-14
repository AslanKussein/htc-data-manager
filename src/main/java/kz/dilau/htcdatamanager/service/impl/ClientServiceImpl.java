package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.ClientPhoneNumber;
import kz.dilau.htcdatamanager.domain.Client;
import kz.dilau.htcdatamanager.domain.ClientFile;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.ClientPhoneNumberRepository;
import kz.dilau.htcdatamanager.repository.ClientFileRepository;
import kz.dilau.htcdatamanager.repository.ClientRepository;
import kz.dilau.htcdatamanager.service.ClientService;
import kz.dilau.htcdatamanager.web.dto.ClientPhoneNumbersDto;
import kz.dilau.htcdatamanager.web.dto.ClientDto;
import kz.dilau.htcdatamanager.web.dto.ClientFileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientPhoneNumberRepository clientPhoneNumberRepository;
    private final ClientFileRepository clientFileRepository;

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


    @Transactional
    public ClientDto saveClient(Client client, ClientDto dto) {
        client.setFirstName(dto.getFirstName());
        client.setSurname(dto.getSurname());
        client.setPatronymic(dto.getPatronymic());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setEmail(dto.getEmail());
        client.setGender(dto.getGender());
        client.getClientPhoneNumberList().clear();
        if (!CollectionUtils.isEmpty(dto.getClientPhoneNumbersDtoList())) {
            List<ClientPhoneNumber> clientPhoneNumberList = new ArrayList<>();
            for (ClientPhoneNumbersDto obj : dto.getClientPhoneNumbersDtoList()) {
                ClientPhoneNumber clientPhoneNumber = new ClientPhoneNumber();
                if (obj.getId() != null) {
                    clientPhoneNumber.setId(obj.getId());
                }
                clientPhoneNumber.setPhoneNumber(obj.getPhoneNumber());
                clientPhoneNumber.setClient(client);
                if (obj.getPhoneNumber().isEmpty()) {
                    if (obj.getId() != null) {
                        clientPhoneNumberRepository.delete(clientPhoneNumber);
                    }
                } else {
                    clientPhoneNumberList.add(clientPhoneNumber);
                }
            }
            client.getClientPhoneNumberList().addAll(clientPhoneNumberList);
        }
        if (!CollectionUtils.isEmpty(dto.getClientFileDtoList())) {
            List<ClientFile> clientFileList = new ArrayList<>();
            for (ClientFileDto obj : dto.getClientFileDtoList()) {
                ClientFile clientFile = new ClientFile();
                if (obj.getId() != null) {
                    clientFile.setId(obj.getId());
                }
                clientFile.setGuid(obj.getGuid());
                clientFile.setClient(client);
                if (obj.getGuid().isEmpty()) {
                    if (obj.getId() != null) {
                    clientFileRepository.delete(clientFile);
                    }
                } else {
                    clientFileList.add(clientFile);
                }
            }
            client.getClientFileList().addAll(clientFileList);
        }

        client.setGender(dto.getGender());
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
                throw EntityRemovedException.createClientRemoved(id);
            }
            return optionalClient.get();
        } else {
            throw NotFoundException.createClientById(id);
        }
    }

    @Override
    public ClientDto update(ClientDto dto) {
        Optional<Client> optionalClient = clientRepository.findById(dto.getId());
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();

            if (!client.getPhoneNumber().equals(dto.getPhoneNumber())) {
                if (clientRepository.existsByPhoneNumberIgnoreCase(dto.getPhoneNumber())) {
                    throw BadRequestException.createClientHasFounded(dto.getPhoneNumber());
                }
            }
            if (!client.getEmail().equalsIgnoreCase(dto.getEmail())) {
                if (clientRepository.existsByEmailIgnoreCase(dto.getEmail())) {
                    throw BadRequestException.createEditEmail(dto.getEmail());
                }
            }

            client.setSurname(dto.getSurname());
            client.setFirstName(dto.getFirstName());
            client.setPatronymic(dto.getPatronymic());
            client.setLocation(dto.getLocation());
            client.setPhoneNumber(dto.getPhoneNumber());
            client.setEmail(dto.getEmail());
            client.setBirthDate(dto.getBirthDate());
            client = clientRepository.save(client);

            return new ClientDto(client);
        } else {
            throw NotFoundException.createClientById(dto.getId());
        }
    }
}
