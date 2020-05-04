
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

import entity.Agency;
import proxy.ApiGatewayProxyResponse;
import proxy.ApiGatewayRequest;
import service.AgentService;

public class GetAgencyById implements RequestHandler<ApiGatewayRequest, ApiGatewayProxyResponse> {

	private AgentService agentService = new AgentService();

	public ApiGatewayProxyResponse handleRequest(ApiGatewayRequest request, Context context) {
		LambdaLogger logger = context.getLogger();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Access-Control-Allow-Origin", "*");
		try {
			if (request.getPathParameters() == null || request.getPathParameters().get("agencyId") == null) {
				return new ApiGatewayProxyResponse(400, headers, null);
			}
			Agency agency = agentService.getAgencyById(Long.parseLong(request.getPathParameters().get("agencyId")));
			if (agency == null) {
				return new ApiGatewayProxyResponse(404, headers, null);
			}
			return new ApiGatewayProxyResponse(200, headers, new Gson().toJson(agency));

		} catch (NumberFormatException | SQLException e) {
			logger.log(e.getMessage());
			return new ApiGatewayProxyResponse(400, headers, null);
		} catch (ClassNotFoundException e) {
			logger.log(e.getMessage());
			return new ApiGatewayProxyResponse(500, headers, null);
		}
	}
}