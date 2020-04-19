
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

import entity.Agency;
import proxy.ApiGatewayProxyResponse;
import proxy.ApiGatewayRequest;
import util.ConnectUtil;

public class GetAgencyById implements RequestHandler<ApiGatewayRequest, ApiGatewayProxyResponse> {

	public ApiGatewayProxyResponse handleRequest(ApiGatewayRequest request, Context context) {
		Agency agency = new Agency();
		Connection connection = null;
		LambdaLogger logger = context.getLogger();
		try {
			if (request.getPathParameters() == null || request.getPathParameters().get("agencyId") == null) {
				return new ApiGatewayProxyResponse(400, null, null);
			}
			connection = ConnectUtil.getInstance().getConnection();
			PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Agency WHERE Agency.agencyId = ?");
			pstmt.setLong(1, Long.parseLong(request.getPathParameters().get("agencyId")));
			ResultSet rs = pstmt.executeQuery();
			if (!rs.next()) {
				return new ApiGatewayProxyResponse(404, null, null);
			}
			agency.setId(rs.getLong("agencyId"));
			agency.setName(rs.getString("agencyName"));
			agency.setAddress(rs.getString("agencyAddress"));
			agency.setPhone(rs.getString("agencyPhone"));
		} catch (NumberFormatException | SQLException e) {
			logger.log(e.getMessage());
			return new ApiGatewayProxyResponse(400, null, null);
		} catch (ClassNotFoundException e) {
			logger.log(e.getMessage());
			return new ApiGatewayProxyResponse(500, null, null);
		}
		return new ApiGatewayProxyResponse(200, null, new Gson().toJson(agency));
	}
}