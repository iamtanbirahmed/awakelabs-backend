package com.awakelabs.readservice.service;

import com.awakelabs.readservice.component.EncryptionKeyComponent;
import com.awakelabs.readservice.dto.VitalSignReadDTO;
import com.awakelabs.readservice.model.VitalSign;
import com.awakelabs.readservice.repository.VitalSignRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class VitalSignReadService {

    private EncryptionKeyComponent encryptionKeyComponent;
    private VitalSignRepository vitalSignRepository;
    private ModelMapper modelMapper;

    /**
     * Transforms the raw Vital Signs PHI values into json values
     * @return
     */

    public List<VitalSignReadDTO> getAllVitalSign() {
        List<VitalSign> vitalSigns = this.vitalSignRepository.findAllEncrypted(this.encryptionKeyComponent.getVitalSignSecret());
        List<VitalSignReadDTO> vitalSignReadDTOS = new ArrayList<>();
        vitalSigns.stream().forEach(vitalSign -> {
                    VitalSignReadDTO vitalSignReadDTO = modelMapper.map(vitalSign, VitalSignReadDTO.class);
                    // breaks the data stream into PHI values.
                    String[] phiValues = vitalSign.getPhiValues().split("#");
                    vitalSignReadDTO.setAnxietyLevel(Double.valueOf(phiValues[0]));
                    vitalSignReadDTO.setBaselineProgress(Double.valueOf(phiValues[1]));
                    vitalSignReadDTO.setCurrentBpm(Double.valueOf(phiValues[2]));
                    vitalSignReadDTOS.add(vitalSignReadDTO);

                }

        );

        return vitalSignReadDTOS;
    }

    //    public List<VitalSign> getAllVitalSign() {
//        return (List<VitalSign>) this.vitalSignRepository.findAll();
//    }

}



