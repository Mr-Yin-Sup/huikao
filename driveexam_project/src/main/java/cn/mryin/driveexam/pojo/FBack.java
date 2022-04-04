package cn.mryin.driveexam.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author MrYin
 * @date 2021/12/9
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FBack implements Serializable {
    private int fBackId;
    private int uId;
    private String fBackMsg;
    private String fState;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String time;
    private User user;
}
