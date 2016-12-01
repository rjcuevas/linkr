package com.linkr.user.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserInfo.class)
public abstract class UserInfo_ {

	public static volatile SingularAttribute<UserInfo, String> first_name;
	public static volatile SingularAttribute<UserInfo, Long> userID;
	public static volatile SingularAttribute<UserInfo, String> email;
	public static volatile SingularAttribute<UserInfo, String> last_name;
	public static volatile SingularAttribute<UserInfo, UserAccount> user;

}

