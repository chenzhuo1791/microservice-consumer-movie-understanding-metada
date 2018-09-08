package spring.controller;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import spring.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class MovieController {

	private Logger logger = LoggerFactory.getLogger(MovieController.class);

	@Autowired
	private RestTemplate restTemplate ;

	@GetMapping("/findUser/{userId}")
	public User findUserById(@PathVariable Long userId){
		String url = "http://127.0.0.1:8000/" + userId + "/showUser";
		User user = this.restTemplate.getForObject(url, User.class);
		logger.info("  result = {} ", user);
		return user ;
	}

    @Autowired
	private DiscoveryClient discoveryClient ;

    private String instance_provider_consumer= "microservice-consumer-movie";

    private String instance_provider_user = "microservice-provider-user";

    @GetMapping("/user-instances")
    public List<ServiceInstance> showServiceInfo() {
        List<ServiceInstance> serviceInstanceList = this.discoveryClient.getInstances(instance_provider_user);
        serviceInstanceList.addAll(this.discoveryClient.getInstances(instance_provider_consumer));
        return serviceInstanceList;
    }
}
