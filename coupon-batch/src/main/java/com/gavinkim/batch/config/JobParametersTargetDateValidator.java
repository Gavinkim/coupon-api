package com.gavinkim.batch.config;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JobParametersTargetDateValidator implements JobParametersValidator {
    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        JobParameter jobParameter = parameters.getParameters().get("targetDate");
        if(jobParameter != null){
            String value = (String) jobParameter.getValue();
            Pattern pattern = Pattern.compile("^(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[0-1])$");
            Matcher matcher = pattern.matcher(value);
            if(!matcher.matches()){
                throw new JobParametersInvalidException("parameter 형식: (yyyyMMdd) 을 확인 바랍니다. input="+value);
            }
        }
    }
}
