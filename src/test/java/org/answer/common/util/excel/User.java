package org.answer.common.util.excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright(c) 2017-2019, 北京希为科技有限公司
 * Beijing Xiwei Technology Co., Ltd.
 * All rights reserved.
 *
 * @author 林建明
 * @version 1.0
 * @date 2019-05-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;

    private String userName;

    private String password;

    private String email;

    private String sex;

    private String valid;

    private String createTime;

    private String updateTime;

}