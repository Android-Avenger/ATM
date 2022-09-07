package com.tayyba.atm;

import com.tayyba.atm.models.User;

public interface UserInfoEventListener {
    void onUserFound(User user);
}
