package cn.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Admin implements Serializable {

  private String id;
  private String username;
  private String password;
  private long status;
}
