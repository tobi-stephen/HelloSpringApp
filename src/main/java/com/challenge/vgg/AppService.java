package com.challenge.vgg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class AppService {

    private final AppRepository appRepository;

    @Autowired
    public AppService(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public ResponseEntity<String> createOrUpdateEntity(AppRequest appRequest, String username) {
        LocalDate localDate = LocalDate.parse(appRequest.getDateOfBirth());
        Date dateOfBirth = Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        if (!dateOfBirth.before(generateTodayDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }

        long id = appRequest.getId();
        AppModel appModel = new AppModel();
        appModel.setUsername(username);
        appModel.setDateOfBirth(dateOfBirth);
//        appModel.setId(id);

        appRepository.save(appModel);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    public ResponseEntity<String> fetchEntity(String username) {

        Optional<AppModel> appModel = appRepository.findByUsername(username);
        if (!appModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
        }

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Date today = generateTodayDate();
        Date dateOfBirth = appModel.get().getDateOfBirth();
        Calendar clUser = new GregorianCalendar();
        clUser.setTime(dateOfBirth);
        clUser.set(Calendar.YEAR, currentYear);

        String response = "";
        Date clUserTime = clUser.getTime();
        if (clUserTime.equals(today)) {
            response = String.format("Hello %s, Happy Birthday!", username);
        } else {
            if (clUserTime.before(today)) { // if birthday for the present year is past, look at the following year
                clUser.set(Calendar.YEAR, currentYear + 1);
            }
            clUserTime = clUser.getTime();
            long diff = clUserTime.getTime() - today.getTime();
            long days = TimeUnit.MILLISECONDS.toDays(diff);
            response = String.format("Hello %s, your birthday is in %s days time.", username, days);

        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private Date generateTodayDate() {
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.HOUR_OF_DAY, 0);
        cl.set(Calendar.MINUTE, 0);
        cl.set(Calendar.SECOND, 0);
        cl.set(Calendar.MILLISECOND, 0);
        Date today = cl.getTime();

        return today;
    }
}