import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.amazonaws.services.lambda.runtime.Context;

import proxy.ApiGatewayProxyResponse;
import proxy.ApiGatewayRequest;

public class GetAgencyByIdTests {

	@Test
	public void getAgencyById() {
		ApiGatewayRequest request = new ApiGatewayRequest();
		Map<String, String> pathParameters = new HashMap<String, String>();
		pathParameters.put("agencyId", "1");
		request.setPathParameters(pathParameters);
		Context context = new MockContext();
		ApiGatewayProxyResponse response = (ApiGatewayProxyResponse) new GetAgencyById().handleRequest(request,
				context);
		assertEquals(200, response.getStatusCode());
	}

	@Test
	public void getAgencyById400() {
		ApiGatewayRequest request = new ApiGatewayRequest();
		Map<String, String> pathParameters = new HashMap<String, String>();
		pathParameters.put("agencyId", "a");
		request.setPathParameters(pathParameters);
		Context context = new MockContext();
		ApiGatewayProxyResponse response = (ApiGatewayProxyResponse) new GetAgencyById().handleRequest(request,
				context);
		assertEquals(400, response.getStatusCode());
	}

	@Test
	public void getAgencyById404() {
		ApiGatewayRequest request = new ApiGatewayRequest();
		Map<String, String> pathParameters = new HashMap<String, String>();
		pathParameters.put("agencyId", "0");
		request.setPathParameters(pathParameters);
		Context context = new MockContext();
		ApiGatewayProxyResponse response = (ApiGatewayProxyResponse) new GetAgencyById().handleRequest(request,
				context);
		assertEquals(404, response.getStatusCode());
	}

}
