package com.awakelabs.readservice.repository;

import com.awakelabs.readservice.model.VitalSign;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;


@Repository
public interface VitalSignRepository extends CrudRepository<VitalSign, UUID> {

    /**
     * method to pull the vital signs from the database
     * @param key the symmetric to decrypt
     * @return
     */
    @Query(value = "SELECT * FROM  get_phi_encrypted_vital_sign(:sym_key)", nativeQuery = true)
    public List<VitalSign> findAllEncrypted(@Param("sym_key") String key);


}













//    @Query(value = "SELECT * FROM " +
//            "create_phi_encrypted_vital_sign_fn(" +
//            ":phi_values" + ","+
//            ":patient_id" + ","+
//            ":time" + ","+
//            ":battery_level" + ","+
//            ":sym_key" +
//            ")",
//            nativeQuery = true)
//    public String saveEncrypted(
//            @Param("phi_values") String phiValues,
//            @Param("patient_id") Integer patientId,
//            @Param("time") Long time,
//            @Param("battery_level") Double batteryLevel,
//            @Param("sym_key") String key
//    );



    //    @Query(value = "SELECT * FROM " +
//            "create_encrypted_vital_sign_fn(" +
//            ":anxiety_level" + ","+
//            ":baseline_progress" + ","+
//            ":current_bpm" + ","+
//            ":patient_id" + ","+
//            ":time" + ","+
//            ":battery_level" + ","+
//            ":sym_key" +
//            ")",
//            nativeQuery = true)
//    public String saveEncrypted(
//            @Param("anxiety_level") Double anxietyLevel,
//            @Param("baseline_progress") Double baselineProgress,
//            @Param("current_bpm") Double currentBpm,
//            @Param("patient_id") Integer patientId,
//            @Param("time") Long time,
//            @Param("battery_level") Double batteryLevel,
//            @Param("sym_key") String key
//    );
//
//
//    @Query(value = "SELECT * FROM  get_encrypted_vital_sign(:sym_key)", nativeQuery = true)
//    public List<VitalSign> findAllEncrypted(@Param("sym_key") String key);

