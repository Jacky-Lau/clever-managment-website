angular.module('clever.management.directives', ['clever.management.directives.splitter', 'clever.management.directives.overview', 'clever.management.directives.classificationView', 'clever.management.directives.filesModel', 'clever.management.directives.fileModel', 'clever.management.directives.headerTab', 'clever.management.directives.terminologyTab', 'clever.management.directives.definitionTab', 'clever.management.directives.resizable', 'angularBootstrapNavTree', 'toggle-switch']);
angular.module('clever.management.services', ['clever.management.services.fileUpload', 'clever.management.services.archetypeRetrieve', 'clever.management.services.archetypeParse', 'clever.management.services.appLibrary', 'clever.management.services.layout', 'clever.management.services.classification', 'clever.management.services.msgbox', 'clever.management.services.busy']);
angular.module('clever.management.filters', ['clever.management.filters.pretty', 'clever.management.filters.unsafe', 'clever.management.filters.timestamp']);
angular.module('clever.management.controllers', ['clever.management.controllers.app', 'clever.management.controllers.archetypeDisplayCtrl']);
angular.module('cleverManagementApp', ['ngRoute', 'ui.bootstrap', 'ui.utils', 'ng-context-menu', 'pascalprecht.translate', 'clever.management.directives', 'clever.management.controllers', 'clever.management.services', 'clever.management.filters', 'clever.management.config']).config(function($routeProvider, $translateProvider) {
	// route config
	$routeProvider.when("/", {
		templateUrl : 'home.html',
		controller : HomeCtrl
	}).when("/management", {
		templateUrl : 'management.html',
		controller : ManagementCtrl
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
		iReturn : 'Return',
		iDelete : 'Delete',
		iSucceeded : 'Succeeded',
		iFailed : 'Failed',
		iLoading : 'Loading...',
		iSaving : 'Saving...',
		iDeploying : 'Deploying...',
		iSaveLayoutQuiz : 'Do you want to save Layout?',
		iSaveLayoutSucceeded : 'Save layout succeeded.',
		iSaveLayoutFailed : 'Save layout failed.',
		iAddAppSucceeded : 'Add application succeeded.',
		iAddAppFailed : 'Add application failed.',
		iEditAppSucceeded : 'Edit application succeeded.',
		iEditAppFailed : 'Edit application failed.',
		iDeleteAppQuiz : 'Are you sure you want to delete this application?',
		iDeleteAppSucceeded : 'Delete application succeeded.',
		
		iLanguage : 'Language',
		zh : 'Chinese',
		en : 'English',
		
		// Header	
		iHome : 'Home',
		iWelcome : 'Welcome,',
		iManageApp : 'Manage Applications',
		iLogOut : 'Log Out',
		iLogIn : 'Log In',	
		// App page
		iAppName : 'Name',
		iAppAdd : 'Add new application',
		iAppEdit : 'Edit application',
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
		iAboutBrief : '',
		iArchetypeView : 'Archetype Overview',
		iArchetypeViewBrief : 'An archetype is a re-usable, formal definition of domain level information, defined in terms of constraints on an information model. The key feature of the archetype approach to computing is a complete separation of information models (such as object models of software, models of database schemas) from domain models. Thus, archetypes are not part of the software or database of a system.',
		iAppLibrary : 'Applications',
		iAppLibraryBrief : '',
		iArchetypeManagement : 'Archetype Management',
		iArchetypeManagementBrief : '',
		// Management page
		iManagementAllArchetypes : 'All archetypes',
		iManagementDeployedArchetypes : 'Deployed archetypes',
		iManagementUpload : 'Upload archetypes',
		iManagementUploadSucceeded : 'Upload succeeded.',
		iManagementUploadFailed : 'Upload failed.。',
		iManagementDeploy : 'Deploy',
		iManagementDeploySucceeded : 'Deploy succeeded.',
		iManagementDeployFailed : 'Deploy failed.',
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
		// About page
		iAboutWhatTitle : '',
		iAboutWhatDetail : '',
		iAboutTecTitle : '',
		iAboutTecDetail : '',
		iAboutDevTitle : '',
		iAboutDevDetail : '',
		
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
		iReturn : '返回',
		iDelete : '删除',
		iSucceeded : '成功',
		iFailed : '失败',
		iLoading : '加载中...',
		iSaving : '保存中...',
		iDeploying : '部署中...',
		iSaveLayoutQuiz : '是否保存布局？',
		iSaveLayoutSucceeded : '保存布局成功。',
		iSaveLayoutFailed : '保存布局失败。',
		iAddAppSucceeded : '添加应用成功。',
		iAddAppFailed : '添加应用失败。',
		iEditAppSucceeded : '修改应用成功。',
		iEditAppFailed : '修改应用失败。',
		iDeleteAppQuiz : '是否确定删除该应用？',
		iDeleteAppSucceeded : '删除应用成功。',
		
		iLanguage : '语言',
		zh : '中文',
		en : '英语',
		// Header	
		iHome : '首页',
		iWelcome : '欢迎您，',	
		iManageApp : '管理应用',
		iLogOut : '注销',
		iLogIn : '登录',		
		// App page
		iAppName : '名称',
		iAppAdd : '添加',
		iAppEdit : '修改',
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
		iAboutBrief : '数据集成平台面向医疗人员，采用openEHR规范和分层建模方法，基于全新的高性能原型关系映射方法，能够适应医疗信息不断发展变化的需要，为医疗机构提供全面统一的数据管理服务。',
		iArchetypeView : 'Archetype总览',
		iArchetypeViewBrief : 'Archetype是可重用的领域信息形式化定义，基于参考信息模型并添加约束实现。Archetype方法的关键特性是信息模型和领域模型完全分离，实现了Archetype与软件系统的独立发展。',
		iAppLibrary : '应用',
		iAppLibraryBrief : '基于CLEVER中标准化结构化的医疗数据可以方便地构建各类医疗软件，并且避免了复杂的医疗信息集成工作，使开发人员集中于功能的设计和开发，易于医疗人员使用，有效促进医疗软件的发展。',
		iArchetypeManagement : 'Archetype管理',
		iArchetypeManagementBrief : '通过委员会集中管理Archetype，能够有效构建和维护一个独立全面高质量的医疗信息模型，并且更易于采用一致的标准医学术语和本体对信息进行标注，能够有效促进医疗信息的标准化和互操作。',
		// Management page
		iManagementAllArchetypes : '所有Archetypes',
		iManagementDeployedArchetypes : '已部署的Archetypes',
		iManagementUpload : '上传',
		iManagementUploadSucceeded : '上传成功。',
		iManagementUploadFailed : '上传失败。',
		iManagementDeploy : '部署',
		iManagementDeploySucceeded : '部署成功。',
		iManagementDeployFailed : '部署失败。',
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
		// About page
		iAboutWhatTitle : '',
		iAboutWhatDetail : '',
		iAboutTecTitle : '技术架构',
		iAboutTecDetail : 'CLEVER采用openEHR分层建模方法和面向服务的软件架构。其中医疗专家制定表达领域信息需求的原型，CLEVER自动通过原型关系映射方法利用原型生成基于关系型数据库的数据存储，并提供了基于原型的数据访问语言，客户端可以通过webservice接口使用原型数据访问语言访问CLEVER中的数据。CLEVER还提供了基于原型自动生成的数据录入界面，方便医疗人员录入医疗机构内医疗信息化尚未支持的重要医疗信息。随着医疗信息需求的变化，医疗专家对原型进行维护更新即可使CLEVER适应不断变化的医疗信息需求。',
		iAboutDevTitle : '开发方法',
		iAboutDevDetail : '基于CLEVER的数据集成平台的核心开发原则是医疗领域与IT领域分离，医疗专家和IT专家可以分别关注数据集成问题的医疗知识领域和IT技术领域，使医疗信息的复杂动态性不会影响到系统的结构，并且不必等待形成完整的医疗信息模型，能够进行高效的迭代开发，提高了开发过程的敏捷性。医疗专家能够定义和维护一个独立全面高质量的医疗信息库，更易于采用标准医学术语和本体，通过采用委员会的方式集中管理和讨论，能够有效促进医疗信息的标准化和互操作。一些重要的软件组成部分可以通过基于原型生成的方式进行构建，显著减少编码工作。基于CLEVER提供的标准接口和原型数据访问语言，可以方便地构建各类应用，应用获取的都是标准化结构化的数据，易于处理和使用。',
	});
	$translateProvider.preferredLanguage('zh');
}).run(function($rootScope, $location, $http, busyService, AUTHENTICATION_URL) {
	// register listener to watch route changes
	$rootScope.$on("$routeChangeStart", function(event, next, current) {
		busyService.setBusy(true);
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
		busyService.setBusy(false);
	});
});
