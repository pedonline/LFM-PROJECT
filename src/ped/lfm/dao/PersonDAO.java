package ped.lfm.dao;


import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Session;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.ogm.OgmSession;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.neo4j.unsafe.impl.batchimport.input.DataException;

import com.plw.util.Common;
import com.plw.util.DataNotFoundException;

import ped.lfm.common.TYPE_DATA_STATUS;
import ped.lfm.model.Person;
import ped.lfm.util.CommonLog;
import ped.lfm.util.LOG_LEVEL;

public class PersonDAO {
	
	public static Person Add(Session MyHSs,Person ao_Person, String as_UserName, Integer SUID, Integer SGID) throws Exception {
		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, "PersonDAO", "Add", "[START]");
		try {
			ao_Person.setModifyTime(Common.GetCurrTime());
			ao_Person.setModifyUser(as_UserName);
			ao_Person.setCreateTime(Common.GetCurrTime());
			ao_Person.setCreateUser(as_UserName);
			MyHSs.save(ao_Person);
			return ao_Person;
		} catch (Exception ex) {
			CommonLog.Print(LOG_LEVEL.ERROR_LEVEL, "PersonDAO", "Add",  ex.toString());			
			throw ex;
		} finally {
		}
	}

	public static Person Modify(Session MyHSs,Person ao_Person, String as_UserName, Integer SUID, Integer SGID) throws Exception {
		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, "PersonDAO", "Add", "[START]");
		try {
			ao_Person.setModifyTime(Common.GetCurrTime());
			ao_Person.setModifyUser(as_UserName);
			MyHSs.update(ao_Person);
			return ao_Person;
		} catch (Exception ex) {
			CommonLog.Print(LOG_LEVEL.ERROR_LEVEL, "PersonDAO", "Add",  ex.toString());			
			throw ex;
		} finally {
		}
	}
	
	public static Person Disable(Session MyHSs, Person ao_Person, String as_UserName, Integer SUID, Integer SGID) throws Exception {
		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, "PersonDAO", "Disable", "[BEGIN]");
		try {
			ao_Person.setModifyTime(Common.GetCurrTime());
			ao_Person.setModifyUser(as_UserName);
			ao_Person.setDataStatus(TYPE_DATA_STATUS.STATUS_DISABLE.getValue());
			MyHSs.saveOrUpdate(ao_Person);
			CommonLog.Print(LOG_LEVEL.INFO_LEVEL, "PersonDAO", "Disable", "[END]");
			return ao_Person;
		} catch (Exception ex) {
			CommonLog.Print(LOG_LEVEL.ERROR_LEVEL, "PersonDAO", "Disable", "[ERROR] : " + ex.toString());
			throw ex;
		} finally {
		}
	}

	public static Person Delete(Session MyHSs, Person ao_Person, Integer SUID, Integer SGID) throws Exception {
		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, "PersonDAO", "Delete", "[BEGIN]");
		try {
			MyHSs.delete(ao_Person);
			CommonLog.Print(LOG_LEVEL.INFO_LEVEL, "PersonDAO", "Delete", "[END]");
			return ao_Person;
		} catch (Exception ex) {
			CommonLog.Print(LOG_LEVEL.ERROR_LEVEL, "PersonDAO", "Delete", "[ERROR] : " + ex.toString());
			throw ex;
		} finally {
		}
	}
	
	public static Person GetByID(Session MyHSs, Integer ai_PersonID,Integer SUID,Integer SGID) throws DataNotFoundException {
		if(ai_PersonID == null) {
			throw new DataException("PersonID [" + ai_PersonID + "] is not found.");
		}
		Person thePerson = null;
//		OgmSession session = (OgmSession) MyHSs;
		CommonLog.Print(LOG_LEVEL.INFO_LEVEL, "PersonDAO", "GetByID", "[START] @@@");
//		String query1 = "db.Person.find({ '_id' : "+ai_PersonID+" }) ";
//		NativeQuery  Query = session.createNativeQuery( query1 ).addEntity( Person.class );
		thePerson = MyHSs.get(Person.class, ai_PersonID.longValue());
//		CommonLog.Print(LOG_LEVEL.ERROR_LEVEL, "PersonDAO", "Query : ",Query.getQueryString()+ " @@@");
		
//		thePerson =  (Person) Query.uniqueResult();
		if (thePerson != null) {
			return thePerson;
		} else {
			CommonLog.Print(LOG_LEVEL.ERROR_LEVEL, "PersonDAO", "GetByID", "[Not Found] @@@");
			throw new DataNotFoundException("PersonID [" + ai_PersonID + "] is not found.");
		}
	}

}
