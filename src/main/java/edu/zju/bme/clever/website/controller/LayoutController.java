package edu.zju.bme.clever.website.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.zju.bme.clever.website.exception.LayoutException;
import edu.zju.bme.clever.website.service.LayoutService;
import edu.zju.bme.clever.website.view.entity.LayoutInfo;
import edu.zju.bme.clever.website.view.entity.LayoutSettingInfo;

@Controller
public class LayoutController {

	@Resource(name = "layoutService")
	private LayoutService layoutService;

	@RequestMapping(value = "/layouts", method = RequestMethod.GET)
	@ResponseBody
	public List<LayoutInfo> getAllLayouts() {
		return this.layoutService.getAllLayouts();
	}

	@RequestMapping(value = "/layout/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<LayoutSettingInfo> getLayoutById(@PathVariable Integer id) {
		return this.layoutService.getLayoutById(id);
	}

	@RequestMapping(value = "/layout/id/{id}", method = RequestMethod.POST)
	@ResponseBody
	public FileUploadResult updateLayoutById(@PathVariable Integer id,
			@RequestBody List<LayoutSettingInfo> infos) {
		FileUploadResult result = new FileUploadResult();
		result.setSucceeded(true);
		try {
			this.layoutService.updateLayout(id, infos);
		} catch (LayoutException ex) {
			result.setSucceeded(false);
			result.setMessage(ex.getMessage());
			return result;
		}
		return result;
	}
}
