package service;

import java.sql.Connection;
import java.sql.SQLException;

import dao.AgencyDao;
import entity.Agency;
import util.ConnectUtil;

public class AgentService {

	public Agency getAgencyById(Long agencyId) throws ClassNotFoundException, SQLException {
		Connection connection = null;
		try {
			connection = ConnectUtil.getInstance().getConnection();
			return new AgencyDao(connection).get(agencyId);
		} catch (SQLException e) {
			throw e;
		}
	}
}
