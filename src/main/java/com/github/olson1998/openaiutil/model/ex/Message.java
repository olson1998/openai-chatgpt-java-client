package com.github.olson1998.openaiutil.model.ex;

import com.github.olson1998.openaiutil.model.chat.RoleAttribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private RoleAttribute role;

    private String content;

}
