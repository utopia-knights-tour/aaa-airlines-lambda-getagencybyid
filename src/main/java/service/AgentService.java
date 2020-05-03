package service;

import java.sql.Connection;
import java.sql.SQLException;

import dao.AgencyDao;
import datasource.HikariCPDataSource;
import entity.Agency;

public class AgentService {

	public Agency getAgencyById(Long agencyId) throws ClassNotFoundException, SQLException {
		Connection connection = null;
		try {
			connection = HikariCPDataSource.getConnection();
			return new AgencyDao(connection).get(agencyId);
		} catch (SQLException e) {
			throw e;
		} finally {
			connection.close();
		}
	}
}
