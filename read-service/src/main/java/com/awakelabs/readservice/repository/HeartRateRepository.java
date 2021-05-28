package com.awakelabs.readservice.repository;

import com.awakelabs.readservice.model.HeartRate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HeartRateRepository extends CrudRepository<HeartRate, UUID> {


    /**
     * Method to pull data from the database
     * @param key the symmetric key to decrypt the phi instances
     * @return
     */

    @Query(value = "SELECT * from get_phi_encrypted_heart_rate(:sym_key)", nativeQuery = true)
    List<HeartRate> findAllEncrypted(@Param("sym_key") String key);

}











//    @Query(value = "SELECT * FROM " +
//            "create_phi_encrypted_heart_rate_fn(" +
//            ":phi_values" + "," +
//            ":motion" + "," +
//            ":patient_id" + "," +
//            ":time" + "," +
//            ":vital_sign_id" + "," +
//            ":sym_key" +
//            ")",
//            nativeQuery = true)
//    public String saveEncrypted(
//            @Param("phi_values") String phiValues,
//            @Param("motion") Boolean motion,
//            @Param("patient_id") Integer patientId,
//            @Param("time") Long time,
//            @Param("vital_sign_id") UUID vitalSignId,
//            @Param("sym_key") String key
//    );

//    @Query(value= "SELECT * from get_encrypted_heart_rate(:sym_key)", nativeQuery = true)
//    List<HeartRate> findAllEncrypted(@Param("sym_key") String key);
//
//
//    @Query(value = "SELECT * FROM " +
//            "create_encrypted_heart_rate_fn(" +
//            ":anxiety_level" + ","+
//            ":heart_rate" + ","+
//            ":motion" + ","+
//            ":patient_id" + ","+
//            ":rr_interval" + ","+
//            ":time" + ","+
//            ":vital_sign_id" + ","+
//            ":sym_key" +
//            ")",
//            nativeQuery = true)
//    public String saveEncrypted(
//            @Param("anxiety_level") Double anxietyLevel,
//            @Param("heart_rate") Double heartRate,
//            @Param("motion") Boolean motion,
//            @Param("patient_id") Integer patientId,
//            @Param("rr_interval") Double rrInterval,
//            @Param("time") Long time,
//            @Param("vital_sign_id") UUID vitalSignId,
//            @Param("sym_key") String key
//    );