@PostMapping("/parse_Velocity_Template")
	public Map<String, Object> parseVelocityTemplate(@RequestBody TemplateRequest request) {
		log.info("Received request to parse templates");

		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.init();
		log.info("VelocityEngine initialized");

		// Create the VelocityContext from the request context
		VelocityContext context = createVelocityContext(request.getContext());
		log.info("VelocityContext created with context map: {}", request.getContext());

		// Process each template and collect them into a list
		List<String> processedTemplates = request.getTemplates().stream()
				.map(template -> {
					log.info("Processing template: {}", template);
					return evaluateVelocityTemplate(context, template, velocityEngine);
				})
				.collect(Collectors.toList());

		log.info("Processed {} templates", processedTemplates.size());

		// Prepare the response
		Map<String, Object> response = new HashMap<>();
		response.put("templates", processedTemplates);

		log.info("Returning processed templates: {}", processedTemplates);
		return response;
	}

	// Method to create VelocityContext from Map<String, Object>
	private VelocityContext createVelocityContext(Map<String, Object> contextMap) {
		VelocityContext context = new VelocityContext();
		contextMap.forEach((key, value) -> {
			log.info("Adding context variable: {} = {}", key, value);
			context.put(key, value);
		});
		return context;
	}

	// Evaluate each template and build the response
	private String evaluateVelocityTemplate(VelocityContext context, String template, VelocityEngine velocityEngine) {
		StringWriter writer = new StringWriter();
		try {
			log.info("Evaluating template");
			velocityEngine.evaluate(context, writer, "TemplateEvaluation", template);
			log.info("Template evaluated successfully");
		} catch (Exception e) {
			log.error("Error while evaluating template", e);
		}
		return writer.toString();  // Returning the processed template as String
	}
	
	
	
	package com.xyz;

import java.util.List;
import java.util.Map;

public class TemplateRequest {
    private List<String> templates;
    private Map<String, Object> context;

    public List<String> getTemplates() {
        return templates;
    }
    public Map<String, Object> getContext() {
        return context;
    }

}
