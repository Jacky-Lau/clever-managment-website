angular.module('clever.management.directives', ['clever.management.directives.splitter', 'clever.management.directives.overview', 'clever.management.directives.classificationView', 'clever.management.directives.filesModel', 'clever.management.directives.fileModel', 'clever.management.directives.headerTab', 'clever.management.directives.terminologyTab', 'clever.management.directives.definitionTab', 'clever.management.directives.resizable', 'angularBootstrapNavTree', 'toggle-switch']);
angular.module('clever.management.services', ['clever.management.services.fileUpload', 'clever.management.services.archetypeRetrieve', 'clever.management.services.archetypeParse', 'clever.management.services.appLibrary', 'clever.management.services.layout', 'clever.management.services.classification', 'clever.management.services.msgbox', 'clever.management.services.loading']);
angular.module('clever.management.filters', ['clever.management.filters.pretty', 'clever.management.filters.unsafe', 'clever.management.filters.timestamp']);
angular.module('clever.management.controllers', ['clever.management.controllers.app', 'clever.management.controllers.archetypeDisplayCtrl']);
angular.module('cleverManagementApp', ['ngRoute', 'ui.bootstrap', 'ui.utils', 'ng-context-menu', 'pascalprecht.translate', 'clever.management.directives', 'clever.management.controllers', 'clever.management.services', 'clever.management.filters', 'clever.management.config']).config(function($routeProvider, $translateProvider) {
	// route config
	$routeProvider.when("/", {
		templateUrl : 'home.html',
		controller : HomeCtrl
	}).when("/deploy", {
		templateUrl : 'deploy.html',
		controller : DeployCtrl
	}).when("/upload", {
		templateUrl : 'upload.html',
		controller : UploadCtrl
	}).when("/appLibrary", {
		templateUrl : 'app-library.html',
		controller : AppLibraryCtrl
	}).when("/classification/id/:classificationId/type/id/:typeId", {
		templateUrl : 'archetype.html',
		controller : ArchetypeCtrl
	}).when("/login", {
		templateUrl : 'login.html',
		controller : LoginCtrl
	}).when("/about", {
		templateUrl : 'about.html',
	}).when("/classification/id/:classificationId", {
		templateUrl : 'classification.html',
		controller : ClassificationCtrl,
		resolve : {
			classifications : function(classificationService) {
				return classificationService.then(function(service) {
					return service.getClassifications();
				});
			},
			selectedClassification : function(classificationService, $route) {
				return classificationService.then(function(service) {
					return service.getClassificationById($route.current.params.classificationId);
				});
			}
		}
	}).otherwise({
		redirectTo : '/'
	});
	//translate config
	$translateProvider.translations('en', {
		iTitle : 'Integration Platform',
		iConfirm : 'Confirm',
		iCancel : 'Cancel',
		iZoomIn : 'Zoom in',
		iZoomOut : 'Zoom out',
		iReset : 'Reset',
		iSave : 'Save',
		iShowOutline : 'Show outline',
		iHideOutline : 'Hide outline',
		iLegend : 'Legend',
		iObservation : 'Observation',
		iInstruction : 'Instruction',
		iAction : 'Action',
		iEvaluation : 'Evaluation',
		iDemographic : 'Demographic',
		iLoading : 'Loading...',
		
		iLanguage : 'Language',
		zh : 'Chinese',
		en : 'English',
		
		// Header	
		iHome : 'Home',
		iWelcome : 'Welcome,',
		iUpload : 'Upload Archetypes',
		iDeploy : 'Deploy Archetypes',
		iManageApp : 'Manage App Library',
		iLogOut : 'Log Out',
		iLogIn : 'Log In',	
		// App page
		iAppName : 'Name',
		iAppDescription : 'Description',
		iAppDescriptionPlaceHolder : 'Description (Less than 200 words)',
		iAppUrl : 'URL',
		iAppApplications : 'Applications',
		iAppChooseFile : 'Choose a file',
		// Upload page
		iUploadValidate : 'Validate',
		iUploadReset : 'Reset',
		iUploadUpload : 'Upload',
		iUploadRetry : 'Retry',
		iUploadDelete : 'Delete',
		iUploadTitle : 'Upload Archetypes',
		iUploadAddFiles : 'Add files',
		iUploadToUpload : 'To upload',
		iUploadValid : 'Valid',
		iUploadInvalid : 'Invalid',
		iUploadFailed : 'Failed',
		iUploadProcessing : 'Processing',
		iUploadDetails : 'Details',
		iUploadValidationFailed : 'Validation failed.',
		iUploadValidationPast : 'Validation past. Please press the upload button to start upload.',
		iUploadUploading : 'Uploading...',
		iUploadUploadFailed : 'Upload Failed.',
		iUploadUploadSucceeded : 'Upload succeeded.',
		// Login page
		iLoginTitle : 'Please log in',
		iLoginUserName : 'User name',
		iLoginPassword : 'Password',
		iLoginRememberMe : 'Remember me',
		iLoginInvalid : 'User name or password is not correct.',	
		// Home page
		iAbout : 'About',
		iArchetypeView : 'Archetype View',
		iAppLibrary : 'App Library',
		iArchetypeManagement : 'Archetype Management',
		// Deploy page
		iDeployTitle : 'Deploy archetypes',
		iDeployLatestArchetypes : 'Latest version archetypes',
		iDeployDeployedArchetypes : 'Deployed archetypes',
		iDeployDeploy : 'Deploy',
		iDeployDeploying : 'Deploying...',
		// Classification page
		iClsClassification : 'Clasification',
		iClsShowDetails : 'Show Details',
		// Archetype page
		iOverview : 'Overview',
		iLayout : 'Layout',
		iStack : 'Stack',
		iCircle : 'Circle',
		iOrganic : 'Organic',
		iCustom : 'Custom',
		iFilter : 'Filter by name',
	});
	$translateProvider.translations('zh', {
		iTitle : '数据集成平台',
		iConfirm : '确定',
		iCancel : '取消',
		iZoomIn : '放大',
		iZoomOut : '缩小',
		iReset : '重置',
		iSave : '保存',
		iShowOutline : '显示缩略图',
		iHideOutline : '隐藏缩略图',
		iLegend : '图例',
		iObservation : '观察',
		iInstruction : '指示',
		iAction : '活动',
		iEvaluation : '评估',
		iDemographic : '人口统计学',
		iLoading : '加载中...',
		
		iLanguage : '语言',
		zh : '中文',
		en : '英语',
		// Header	
		iHome : '首页',
		iWelcome : '欢迎您，',
		iUpload : '上传Archetype',
		iDeploy : '部署Archetype',	
		iManageApp : '管理应用',
		iLogOut : '注销',
		iLogIn : '登录',		
		// App page
		iAppName : '名称',
		iAppDescription : '简介',
		iAppDescriptionPlaceHolder : '简介 （200字以内）',
		iAppUrl : 'URL',
		iAppApplications : '应用',
		iAppChooseFile : '选择文件',
		// Upload page
		iUploadValidate : '验证',
		iUploadReset : '重置',
		iUploadUpload : '上传',
		iUploadRetry : '重试',
		iUploadDelete : '删除',
		iUploadTitle : '上传Archetypes',
		iUploadAddFiles : '添加文件',
		iUploadToUpload : '待上传',
		iUploadValid : '有效',
		iUploadInvalid : '无效',
		iUploadFailed : '失败',
		iUploadProcessing : '处理中',
		iUploadDetails : '具体信息',
		iUploadValidationFailed : '验证失败，请点击具体信息查看错误内容。删除错误的原型后重新验证或者修改原型后重新上传。',
		iUploadValidationPast : '验证通过，请点击上传按钮开始上传。',
		iUploadUploading : '正在上传...',
		iUploadUploadFailed : '上传失败。',
		iUploadUploadSucceeded : '上传成功。',
		// Login page
		iLoginTitle : '请登录',
		iLoginUserName : '用户名',
		iLoginPassword : '密码',
		iLoginRememberMe : '记住我',
		iLoginInvalid : '用户名或密码错误',
		// Home page
		iAbout : '关于',
		iArchetypeView : 'Archetype视图',
		iAppLibrary : '应用',
		iArchetypeManagement : 'Archetype管理',
		// Deploy page
		iDeployTitle : '部署Archetypes',
		iDeployLatestArchetypes : '待部署的Archetypes',
		iDeployDeployedArchetypes : '已部署的Archetypes',
		iDeployDeploy : '部署',
		iDeployDeploying : '正在部署...',
		// Classification page
		iClsClassification : '类别',
		iClsShowDetails : '显示Archetype',
		// Overview page
		iOverview : '总览',
		iLayout : '布局',
		iStack : '顺序排列',
		iCircle : '环形排列',
		iOrganic : '有机排列',
		iCustom : '自定义',
		iFilter : '关键字',
	});
	$translateProvider.preferredLanguage('zh');
}).run(function($rootScope, $location, $http, loadingService, AUTHENTICATION_URL) {
	// register listener to watch route changes
	$rootScope.$on("$routeChangeStart", function(event, next, current) {
		loadingService.setLoading(true);
		var classificationReg = new RegExp('/classification/id/.*');
		if (next.originalPath != '/' && next.originalPath != '/appLibrary' && !classificationReg.test(next.originalPath) && next.originalPath != '' && next.originalPath != '/archetype' && next.originalPath != '/about') {
			$http.get(AUTHENTICATION_URL).then(function(response) {
				if (response.data != 'true') {
					$location.path("/login");
				} else if (response.data == 'true' && next.originalPath == '/login') {
					$location.path("/");
				}
			});
		}
	});
	$rootScope.$on("$routeChangeSuccess", function(event, next, current) {
		loadingService.setLoading(false);
	});
});
