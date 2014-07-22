angular.module('clever.management.directives.overview', []).directive('overview', ['layoutService', 'msgboxService',
function(layoutService, msgboxService) {
	return {
		restrict : 'EA',
		scope : {
			archetypesBriefInfo : '=',
			selectArchetype : '&',
			windowHeight: '=',
		},
		templateUrl : 'overview.html',
		transclude : true,
		replace : true,
		controller : function($scope, $element, $attrs){
			
			$scope.isDropdownOpened = false;
			
			$scope.zoomIn = function(){
				$scope.graph.zoomIn();
			};
			
			$scope.zoomOut = function(){
				$scope.graph.zoomOut();
			};
			
			$scope.selectLayout = function(layout){
				$scope.currentLayout = layout;
				$scope.isDropdownOpened = false;
			};
		},
		link : function(scope, element, attrs) {

			// Checks if the browser is supported
			if (!mxClient.isBrowserSupported()) {
				
				// Displays an error message if the browser is not supported.
				mxUtils.error('Browser is not supported!', 200, false);
				
			} else {
				
				var container = element.find('#overview-container')[0];
				
				// Creates the graph inside the given container
				scope.graph = new mxGraph(container);
				
				scope.graph.centerZoom = false;
				
				// Enables HTML markup in all labels
				scope.graph.setHtmlLabels(true);
				
				// Override folding to allow for tables
				scope.graph.isCellFoldable = function(cell, collapse) {
					return this.getModel().isVertex(cell);
				};
				
				scope.graph.graphHandler.scaleGrid = true;
				
				// Changes the default vertex style in-place
				var style = scope.graph.getStylesheet().getDefaultVertexStyle();
				style[mxConstants.STYLE_FILLCOLOR] = 'white';
				style[mxConstants.STYLE_STROKECOLOR] = 'white';
				style[mxConstants.CURSOR_MOVABLE_EDGE] = 'default';
				
				// Disable cell resize
				scope.graph.cellsResizable = false;
				
				// Override isCellSelectable
				scope.graph.isCellSelectable = function(cell) {
					if (cell.isEdge()) {
						return false;
					} else {
						return true;
					}
				}; 
				
				// Override isCellMovable
				scope.graph.isCellMovable = function(cell){
					if (cell.isEdge()) {
						return false;
					} else {
						return true;
					}
				};
				
				// Disables built-in context menu
				//mxEvent.disableContextMenu(container);
				
				/*// Enables tooltips
				scope.graph.setTooltips(true);
				
				// Installs a custom tooltip for cells
				scope.graph.getTooltipForCell = function(cell){
					return cell.value.name;
				};*/
				
				// Overrides method to disallow edge label editing
				scope.graph.isCellEditable = function(cell)
				{
					return false;
				};
				
				// Overrides method to provide a cell label in the display
				scope.graph.convertValueToString = function(cell)
				{
					return cell.value.name;
				};
				
				// Enables panning
				scope.graph.setPanning(true);
				// Disables built-in context menu
				mxEvent.disableContextMenu(container);
				// Configures automatic expand on mouseover
				scope.graph.panningHandler.autoExpand = true;

				scope.graph.panningHandler.factoryMethod = function(menu, cell, evt) {
					if (cell != null) {
						var submenu = menu.addItem('Browse version', null, null);
						angular.forEach(cell.value.archetypeInfos, function(info) {
							menu.addItem(info.version, null, function() {
								scope.selectArchetype({
									selectedArchetype : {
										id : info.id,
										name : info.name,
									}
								});
							}, submenu);
						});
					}
				}; 
				
				// Installs a handler for double click events in the graph
				// that shows an alert box				
				scope.graph.addListener(mxEvent.DOUBLE_CLICK, function(sender, evt) {
					var cell = evt.getProperty('cell');
					scope.graph.tooltipHandler.resetTimer();
					if (cell != null) {
						scope.$apply(function(){
							scope.doubleClick({
								selectedArchetype : cell.value
							});
						});				
					}
					evt.consume();
				});		

				// Layouts
				// Circle layout, too big
				var circleLayout = {
					name : 'Circle',
					type : 'system',
					layout : new mxCircleLayout(scope.graph)
				};

				// Compact tree layout, not work
				var compactLayout = {
					name : 'Compact tree',
					type : 'system',
					layout : new mxCompactTreeLayout(scope.graph)
				};

				// Edge label layout, not work
				var edgeLayout = {
					name : 'Edge',
					type : 'system',
					layout : new mxEdgeLabelLayout(scope.graph)
				};

				// Stack layout, work, but very simple
				var stackLayout = {
					name : 'Stack',
					type : 'system',
					layout : new mxStackLayout(scope.graph, true, 30, 10, 10)
				};
				stackLayout.layout.wrap = stackLayout.layout.getParentSize(scope.graph.getDefaultParent()).width;
				
				var organicLayout = {
					name : 'Organic',
					type : 'system',
					layout : new mxFastOrganicLayout(scope.graph)
				};
				//organicLayout.layout.minDistanceLimit = 20;
				//organicLayout.layout.allowedToRun = 20;
				
				scope.layouts = [];
				initLayouts();
				
				// Init layouts				
				function initLayouts() {
					scope.layouts = [];
					layoutService.getAllLayouts().then(function(result) {
						angular.forEach(result, function(layout) {
							layout.type = 'custom';
							scope.layouts.push(layout);
						});
						scope.layouts.push(stackLayout);
						scope.layouts.push(circleLayout);
						scope.layouts.push(organicLayout);
					});
				}	

				scope.saveLayout = function() {
					msgboxService('Save','Do you want to save layout "'+ scope.currentLayout.name +'" ?').result.then(function(isOk){
						if(isOk){
							var parent = scope.graph.getDefaultParent();
					var settings = [];
					angular.forEach(scope.graph.getChildVertices(parent), function(cell) {
						settings.push({
							archetypeHostId: cell.value.id,
							positionX : cell.geometry.x,
							positionY : cell.geometry.y,
						});
					});
					layoutService.updateLayoutById(scope.currentLayout.id, settings).then(function(result) {
						if (result.succeeded) {
							alert("succeeded");
						}
					}); 
						}
					});				
				}; 
			
				function applyLayout(layout) {
					var model = scope.graph.getModel();
					model.beginUpdate();
					try {
						if (layout.type == 'system') {
							layout.layout.execute(scope.graph.getDefaultParent());
						} else if (layout.type == 'custom') {
							layoutService.getLayoutById(layout.id).then(function(result) {
								var parent = scope.graph.getDefaultParent();
								var vertices = scope.graph.getChildVertices(parent);
								angular.forEach(result, function(setting) {
									var vertex = findVertexById(setting.archetypeHostId, vertices);
									var geo = model.getGeometry(vertex);
									var dx = new Number(setting.positionX) - geo.x;
									var dy = new Number(setting.positionY) - geo.y;
									scope.graph.moveCells([vertex], dx, dy);
								});
								angular.forEach(vertices, function(vertex) {
									var geo = model.getGeometry(vertex);
									var dx = new Number(setting.positionX) - geo.x;
									var dy = new Number(setting.positionY) - geo.y;
									scope.graph.moveCells([vertex], dx, dy);
								});
							});
						}
					} finally {
						// Updates the display
						model.endUpdate();
					}
				}

				function findVertexById(id, vertices) {
					for ( i = 0; i < vertices.length; i++) {
						if (vertices[i].value.id == id) {
							var result = vertices[i];
							vertices.splice(i, 1);
							return result;
						}
					}
				}

				scope.$watch('currentLayout', function(newLayout) {
					if(newLayout){
						applyLayout(newLayout);
					}			
				});
				
				// Highlights the vertices when the mouse enters
				// var highlight = new mxCellTracker(graph, '#00FF00');

				// Adds cells to the model in a single step
				var cellWidth = 230;
				var labelWidth = 110;
				

				function getCellById(id, cells) {
					var selectedCell;
					angular.forEach(cells,function(cell){
						if(cell.value.id == id){
							selectedCell = cell;
							return false;
						}
					});
					return selectedCell;
				}
				
				scope.reset = function() {
					scope.graph.getModel().beginUpdate();
					var parent = scope.graph.getDefaultParent();
					scope.graph.view.scale = 1;
					try {
						scope.graph.removeCells(scope.graph.getChildCells(parent));
						var cells = [];
						angular.forEach(scope.archetypesBriefInfo.archetypeHostInfos, function(value, index) {
							var vertex = scope.graph.insertVertex(parent, null, value, 0, 0, cellWidth, 0);
							// Updates the height of the cell (override width
							// for table width is set to 100%)
							scope.graph.updateCellSize(vertex);
							vertex.geometry.width = cellWidth;
							vertex.geometry.alternateBounds = new mxRectangle(0, 0, cellWidth, 27);
							cells.push(vertex);
						});
						angular.forEach(scope.archetypesBriefInfo.archetypeRelationshipInfos, function(relationship) {
							if (relationship.relationType == 'OneToMany') {
								var sourceCell = getCellById(relationship.sourceArchetypeHostId, cells);
								var destinationCell = getCellById(relationship.destinationArchetypeHostId, cells);
								var edge = scope.graph.insertEdge(parent, null, '', sourceCell, destinationCell);
							}
						});
					} finally {
						// Updates the display
						scope.graph.getModel().endUpdate();
					}

					if (scope.currentLayout == organicLayout) {
						applyLayout(organicLayout);
					} else {
						scope.currentLayout = organicLayout;
					}

				}; 

				scope.$watch('archetypesBriefInfo', function(archetypesBriefInfo) {
					if (archetypesBriefInfo) {
						scope.reset();
					}				
				});
				
				function getFixedText(text, width, wordWidth, trimWordCount) {
					wordWidth = wordWidth || 7;
					trimWordCount = trimWordCount || 3;	
					var max = parseInt(width / wordWidth);
					var fixedText = text;
					if (text && text.length > max) {
						fixedText = text.substring(0, max - trimWordCount) + '...';
					}
					return fixedText;
				}
				
				function getArchetypeInfoByVersion(version, archetypeInfos) {
					var archetypeInfo;
					angular.forEach(archetypeInfos, function(info) {
						if (info.version == version) {
							archetypeInfo = info;
							return false;
						}
					});
					return archetypeInfo;
				}

				function getVersionSubTable(archetypeInfos, geo) {
					var length = archetypeInfos.length;
					var subTable = "";
					for ( i = 1; i <= length; i++) {
						var version = "v" + i;
						var archetypeInfo = getArchetypeInfoByVersion(version, archetypeInfos);
						subTable += '<table style="color: black;border:1px solid black;" width="' + geo.width + '" cellpadding="2">' + 
										'<tr><th colspan="2" class="text-center" >' + version + '</th></tr>' + 
									'</table>';
						subTable += '<table style="color: black;border:1px solid black;table-layout: fixed;word-wrap: break-word;word-break: break-all;white-space: pre-wrap;text-align: left;" width="' + (geo.width - 9) + '" cellpadding="2">';
						var maxRows = 10;
						angular.forEach(archetypeInfo.archetypeNodeInfos, function(nodeInfo, index) {
							if(index < maxRows){
								if (nodeInfo.type == 'Add') {
									subTable += '<tr>' +
													'<td width="' + (geo.width - 1) + '"><sapn style="padding-left: 2px;padding-right: 2px;"><sapn style="color: Navy;">' + nodeInfo.currentName + '</span><span style="font-weight: bold;"> : </span><span style="color: SaddleBrown;">' + nodeInfo.rmType + '</span></sapn></td>' + 
												'</tr>';
								} else if (nodeInfo.type == 'Modify') {
									subTable += '<tr>' +
													'<td width="' + (geo.width - 1) + '"><sapn style="padding-left: 2px;padding-right: 2px;"><span style="color: MediumVioletRed;">' + nodeInfo.previousName + ' → ' + nodeInfo.currentName + '</span><span style="font-weight: bold;"> : </span><span style="color: SaddleBrown;">' + nodeInfo.rmType + '</span></sapn></td>' + 
												'</tr>';
								}
							} else if (index == maxRows) {
								subTable += '<tr>' +
												'<td width="' + (geo.width - 1) + '"><sapn style="padding-left: 2px;padding-right: 2px;">......</sapn></td>' + 
											'</tr>';
							} else {
							
							}
						});
						subTable += '</table>';
					}
					return subTable;
				}

		
				// Overrides getLabel to return empty labels for edges and
				// short markup for collapsed cells.
				scope.graph.getLabel = function(cell) {
					if (this.getModel().isVertex(cell)) {
						var archetypeName;
						var geo = this.getCellGeometry(cell);
						if (geo) {
							archetypeName = getFixedText(cell.value.conceptName, geo.width - 8, 8, 8);
							var title = '<table style="color: black;border:1px solid black;" width="' + geo.width + '" cellpadding="2" class="title">' + 
										'<tr><th colspan="2" class="text-center" >' + archetypeName + '</th></tr>' + 
									'</table>';
							if (this.isCellCollapsed(cell)) {
								return title;
							} else {
								return title + 
										'<div style="overflow:auto;" class="overview-' + cell.value.rmEntity.toLowerCase() + '">' + 
											getVersionSubTable(cell.value.archetypeInfos, geo) +
										'</div>';
							}
						}
					    
					} else {
						return '';
					}
				};	
			}
		},
	};
}]);

// MxGraph useful examples: graphlayout, codec, layers, scrollbars, tree, userobject
