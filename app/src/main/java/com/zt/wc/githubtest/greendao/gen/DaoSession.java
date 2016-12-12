package com.zt.wc.githubtest.greendao.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.zt.wc.githubtest.dao.User;
import com.zt.wc.githubtest.dao.Student;

import com.zt.wc.githubtest.greendao.gen.UserDao;
import com.zt.wc.githubtest.greendao.gen.StudentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig studentDaoConfig;

    private final UserDao userDao;
    private final StudentDao studentDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        studentDaoConfig = daoConfigMap.get(StudentDao.class).clone();
        studentDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        studentDao = new StudentDao(studentDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(Student.class, studentDao);
    }
    
    public void clear() {
        userDaoConfig.getIdentityScope().clear();
        studentDaoConfig.getIdentityScope().clear();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

}
