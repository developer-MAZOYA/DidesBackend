package org.dides.studentmanagement.controller;

import org.dides.studentmanagement.model.AdmissionPersonInfo;
import org.dides.studentmanagement.service.AdmissionPersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personal-info")
public class AdmissionPersonInfoController {

    @Autowired
    private AdmissionPersonInfoService personalInfoService;

    @GetMapping("/listAll")
    public ResponseEntity<List<AdmissionPersonInfo>> getAllPersonalInfo() {
        List<AdmissionPersonInfo> personalInfoList = personalInfoService.getAllPersonalInfo();
        return ResponseEntity.ok(personalInfoList);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<AdmissionPersonInfo> getPersonalInfoById(@PathVariable Long id) {
        Optional<AdmissionPersonInfo> personalInfo = personalInfoService.getPersonalInfoById(id);
        return personalInfo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/Add")
    public ResponseEntity<AdmissionPersonInfo> createPersonalInfo(@RequestBody AdmissionPersonInfo personalInfo) {
        try {
            AdmissionPersonInfo createdInfo = personalInfoService.createPersonalInfo(personalInfo);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdInfo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/change-info/{id}")
    public ResponseEntity<AdmissionPersonInfo> updatePersonalInfo(
            @PathVariable Long id,
            @RequestBody AdmissionPersonInfo personalInfo) {
        try {
            AdmissionPersonInfo updatedInfo = personalInfoService.updatePersonalInfo(id, personalInfo);
            return ResponseEntity.ok(updatedInfo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePersonalInfo(@PathVariable Long id) {
        try {
            personalInfoService.deletePersonalInfo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AdmissionPersonInfo> getPersonalInfoByEmail(@PathVariable String email) {
        Optional<AdmissionPersonInfo> personalInfo = personalInfoService.getPersonalInfoByEmail(email);
        return personalInfo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/lastname")
    public ResponseEntity<List<AdmissionPersonInfo>> getPersonalInfoByLastName(@RequestParam String lastname) {
        List<AdmissionPersonInfo> personalInfoList = personalInfoService.getPersonalInfoByLastName(lastname);
        return ResponseEntity.ok(personalInfoList);
    }
}