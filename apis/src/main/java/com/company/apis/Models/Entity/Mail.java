package com.company.apis.Models.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
       private String recipient;
       private String msgBody;
       private String subject;
       private String attachment;
}
