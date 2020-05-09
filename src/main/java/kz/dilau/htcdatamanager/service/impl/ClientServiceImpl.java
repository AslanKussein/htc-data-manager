package kz.dilau.htcdatamanager.service.impl;

import kz.dilau.htcdatamanager.domain.AddPhoneNumber;
import kz.dilau.htcdatamanager.domain.Client;
import kz.dilau.htcdatamanager.domain.ClientFile;
import kz.dilau.htcdatamanager.domain.dictionary.TypeOfElevator;
import kz.dilau.htcdatamanager.exception.BadRequestException;
import kz.dilau.htcdatamanager.exception.EntityRemovedException;
import kz.dilau.htcdatamanager.exception.NotFoundException;
import kz.dilau.htcdatamanager.repository.AddPhoneNumberRepository;
import kz.dilau.htcdatamanager.repository.ClientFileRepository;
import kz.dilau.htcdatamanager.repository.ClientRepository;
import kz.dilau.htcdatamanager.service.ClientService;
import kz.dilau.htcdatamanager.web.dto.AddPhoneNumbersDto;
import kz.dilau.htcdatamanager.web.dto.ClientDto;
import kz.dilau.htcdatamanager.web.dto.ClientFileDto;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final AddPhoneNumberRepository addPhoneNumberRepository;
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

    private ClientDto saveClient(Client client, ClientDto dto) {
        client.setFirstName(dto.getFirstName());
        client.setSurname(dto.getSurname());
        client.setPatronymic(dto.getPatronymic());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setEmail(dto.getEmail());
        client.setGender(dto.getGender());
        client.getAddPhoneNumberList().clear();
        if (!CollectionUtils.isEmpty(dto.getAddPhoneNumbers())) {
            List<AddPhoneNumber> addPhoneNumberList = new ArrayList<>();
            for (AddPhoneNumbersDto obj : dto.getAddPhoneNumbers()) {
                AddPhoneNumber addPhoneNumber = new AddPhoneNumber();
                if (obj.getId() != null) {
                    addPhoneNumber.setId(obj.getId());
                }
                addPhoneNumber.setPhoneNumber(obj.getPhoneNumber());
                addPhoneNumber.setClient(client);
                if (obj.getPhoneNumber().isEmpty()) {
                    addPhoneNumberRepository.delete(addPhoneNumber);
                } else {
                    addPhoneNumberRepository.save(addPhoneNumber);
                    addPhoneNumberList.add(addPhoneNumber);
                }
            }
            client.getAddPhoneNumberList().addAll(addPhoneNumberList);
        }
        if (!CollectionUtils.isEmpty(dto.getClientFiles())) {
            List<ClientFile> clientFileList = new ArrayList<>();
            for (ClientFileDto obj : dto.getClientFiles()) {
                ClientFile clientFile = new ClientFile();
                if (obj.getId() != null) {
                    clientFile.setId(obj.getId());
                }
                clientFile.setGuid(obj.getGuid());
                clientFile.setClient(client);
                if (obj.getGuid().isEmpty()) {
                    clientFileRepository.delete(clientFile);
                } else {
                    clientFileRepository.save(clientFile);
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
