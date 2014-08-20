angular.module('clever.management.i18n.zh', ['pascalprecht.translate']).config(function($translateProvider) {
	$translateProvider.translations('zh', {
		iTitle : '数据集成平台',
		iConfirm : '确定',
		iCancel : '取消',
		iZoomIn : '放大',
		iZoomOut : '缩小',
		iReset : '重置',
		iSave : '保存',
		iEnter : '进入',
		iShowOutline : '显示缩略图',
		iHideOutline : '隐藏缩略图',
		iLegend : '图例',
		iDemographic : '人口统计学',
		iAdminEntry : '就诊',
		iObservation : '观察',
		iEvaluation : '评估',
		iInstruction : '指令',
		iAction : '活动',
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
		iAbout : 'About',
		iAboutBrief : 'CLEVER™数据集成平台用于集成融合医疗机构内部的各类的临床数据，它基于分层建模的设计思想，可直接面向临床人员使用，易于操控，并能够适应医疗信息不断发展变化的需要。',
		iArchetypeView : 'Archetype',
		iArchetypeViewBrief : 'Archetype是CLEVER™的核心，它基于openEHR规范实现，是一套从临床基本概念出发的信息模型，而且与软件系统开发独立，因而可由临床人员直接管理和维护，具备较高灵活性。',
		iDataManager : 'DataManager',
		iDataManagerBrief : 'DataManager负责CLEVER™平台中的数据管理，所有数据均按Archeype进行分类，临床人员可方便地直接进行数据的查询、增加、修改和删除，所有操作都将安全地映射到具体物理存储。',
		iShine : 'SHINE™',
		iShineBrief : 'SHINE™为医疗信息集成引擎，它负责从现有各类异构系统中集成数据到CLEVER™平台，SHINE™提供了基于Archetype的语义集成方法，可方便快速地进行集成配置。',
		iAppLibrary : 'App Library',
		iAppLibraryBrief : 'CLEVER™提供了一整套标准接口和原型数据访问语言，以及基于Archetype自动生成应用软件模块的方法，可在此基础上快速构建各类数据应用软件，有效促进了数据的开发与利用。',
		iArchetypeManagement : 'Archetype管理',
		iArchetypeManagementBrief : '通过委员会集中管理Archetype，能够有效构建和维护一个独立全面高质量的医疗信息模型，并且更易于采用一致的标准医学术语和本体对信息进行标注，有效促进医疗信息的标准化和互操作。',
		// Management page
		iManagementAllArchetypes : '所有Archetypes',
		iManagementDeployedArchetypes : '已部署的Archetypes',
		iManagementUpload : '上传',
		iManagementUploadSucceeded : '上传成功。',
		iManagementUploadFailed : '上传失败。',
		iManagementDeploy : '部署',
		iManagementDeploySucceeded : '部署成功。',
		iManagementDeployFailed : '部署失败。',
		iManagementDraft : '草稿',
		iManagementTeamReview : '审核',
		iManagementPublished : '发布',
		iManagementDeprecated : '弃用',
		iManagementTotal : '全部',
		iManagementDeployed : '已部署',
		iManagementUndeployed : '未部署',
		// Classification page
		iClsClassification : '类别',
		iClsShowDetails : '显示Archetype',
		iOpenEhr : 'openEHR',
		iCliniic : '临床',
		iAll : '全部',
		// Overview page
		iOverview : '总览',
		iLayout : '布局',
		iStack : '顺序排列',
		iCircle : '环形排列',
		iOrganic : '有机排列',
		iCustom : '自定义',
		iFilter : '关键字',
		// About page
		iAboutTitle : '关于',
		iAboutWhatTitle : '',
		iAboutWhatDetail : '',
		iAboutTecTitle : '技术架构',
		iAboutTecDetail : 'CLEVER采用openEHR分层建模方法和面向服务的软件架构。其中医疗专家制定表达领域信息需求的原型，CLEVER自动通过原型关系映射方法利用原型生成基于关系型数据库的数据存储，并提供了基于原型的数据访问语言，客户端可以通过webservice接口使用原型数据访问语言访问CLEVER中的数据。CLEVER还提供了基于原型自动生成的数据录入界面，方便医疗人员录入医疗机构内医疗信息化尚未支持的重要医疗信息。随着医疗信息需求的变化，医疗专家对原型进行维护更新即可使CLEVER适应不断变化的医疗信息需求。',
		iAboutDevTitle : '开发方法',
		iAboutDevDetail : '基于CLEVER的数据集成平台的核心开发原则是医疗领域与IT领域分离，医疗专家和IT专家可以分别关注数据集成问题的医疗知识领域和IT技术领域，使医疗信息的复杂动态性不会影响到系统的结构，并且不必等待形成完整的医疗信息模型，能够进行高效的迭代开发，提高了开发过程的敏捷性。医疗专家能够定义和维护一个独立全面高质量的医疗信息库，更易于采用标准医学术语和本体，通过采用委员会的方式集中管理和讨论，能够有效促进医疗信息的标准化和互操作。一些重要的软件组成部分可以通过基于原型生成的方式进行构建，显著减少编码工作。基于CLEVER提供的标准接口和原型数据访问语言，可以方便地构建各类应用，应用获取的都是标准化结构化的数据，易于处理和使用。',
		iAboutDetail : 'CLEVER™的核心思想是实现医疗领域知识与信息技术实现的分离，使得医疗专家和技术专家可以各司其职，医疗信息的复杂动态性不会影响到系统的结构，不必等待完整的医疗信息模型即可进行高效的迭代开发，提高了开发过程的敏捷性。医疗专家能够自主维护由Archetype组成的信息模型，通过采用标准医学术语和本体，能够有效促进医疗信息的标准化和互操作。CLEVER™提供了一整套标准接口和原型数据访问语言，可以方便地构建各类应用软件。主要的软件模块可以基于Archetype自动生成构建，大幅减少了技术专家的工作量，并使得软件能适应信息的动态变化。',
	});
});
