angular.module('clever.management.directives.classificationView', []).directive('classificationView', ['msgboxService',
function(msgboxService) {
	return {
		restrict : 'EA',
		scope : {
			classificationBriefInfo: '=',
			windowHeight: '=',
			control: '=',
			doubleClick: '&',
			addAlert: '&',
		},
		templateUrl : 'classification-view.html',
		transclude : true,
		replace : true,
		controller : function($scope, $element, $attrs){
			
		},
		link : function(scope, element, attrs) {

			// Checks if the browser is supported
			if (!mxClient.isBrowserSupported()) {
				
				// Displays an error message if the browser is not supported.
				msgboxService('Error', 'Browser is not supported!');
				
			} else {
				
				var container = element.find('#classification-view-container')[0];
				
				var outlineContainer = element.find('#outline-container')[0];
				
				// Disables built-in context menu
				mxEvent.disableContextMenu(container);
				//mxEvent.disableContextMenu(outline);
				
				// Creates the graph inside the given container
				scope.graph = new mxGraph(container);
				
				//scope.graph.centerZoom = false;
				
				// Enables HTML markup in all labels
				scope.graph.setHtmlLabels(true);
				
				// 图形窗口的右上角的周围创建导航提示。  
                scope.outline = new mxOutline(scope.graph, outlineContainer); 
                
                // 要显示的图像的轮廓，去掉下面的代码  
                scope.outline.outline.labelsVisible = true;  
                scope.outline.outline.setHtmlLabels(true);
                
                scope.outline.outline.view.canvas.viewportElement.height.baseVal.value = (scope.windowHeight - 190)/3;
                
                scope.isOutlineHided = false;
                
                // Overrides getLabel to return empty labels for edges and
				// short markup for collapsed cells.	
				scope.graph.getLabel = getLabel;
				scope.outline.outline.getLabel = getLabel;			
				
				// Override folding to allow for tables
				/*scope.graph.isCellFoldable = function(cell, collapse) {
					return this.getModel().isVertex(cell);
				};*/
				
				scope.graph.graphHandler.scaleGrid = true;
				
				// 显示导航线  
                scope.graph.graphHandler.guidesEnabled = true;
				
				// Changes the default vertex style in-place
				var style = scope.graph.getStylesheet().getDefaultVertexStyle();
				style[mxConstants.STYLE_FILLCOLOR] = 'white';
				style[mxConstants.STYLE_STROKECOLOR] = 'white';
				style[mxConstants.CURSOR_MOVABLE_EDGE] = 'default';
				
				style = scope.graph.getStylesheet().getDefaultEdgeStyle();
				style[mxConstants.STYLE_ENDARROW] = mxConstants.ARROW_CLASSIC;
				style[mxConstants.STYLE_STARTARROW] = mxConstants.ARROW_CLASSIC;
				
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
				/*scope.graph.convertValueToString = function(cell)
				{
					return cell.value;
				};*/
				
				// Enables panning
				scope.graph.setPanning(true);
		
				/*// Configures automatic expand on mouseover
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
				}; */
				
				// Installs a handler for double click events in the graph							
				scope.graph.addListener(mxEvent.DOUBLE_CLICK, function(sender, evt) {
					var cell = evt.getProperty('cell');
					scope.graph.tooltipHandler.resetTimer();
					if (cell != null && cell.isVertex()) {
						scope.$apply(function() {
							scope.doubleClick({
								selectedValue : cell.value
							});
						});
					}
					evt.consume();
				});
			
				// Select all
				var keyHandler = new mxKeyHandler(scope.graph);
				keyHandler.bindControlKey(65, function(evt) {
					var parent = scope.graph.getDefaultParent();
					scope.graph.selectVertices(parent);
				});

				function findVertexById(id, vertices) {
					for ( i = 0; i < vertices.length; i++) {
						if (vertices[i].value.id == id) {
							var result = vertices[i];
							vertices.splice(i, 1);
							return result;
						}
					}
				}
				
				scope.control = {
					zoomIn : zoomIn,
					zoomOut : zoomOut,
					reset : reset,
					getCurrentLayout : getCurrentLayout,
				}; 
				
				function zoomIn() {
					scope.graph.zoomIn();
				}

				function zoomOut() {
					scope.graph.zoomOut();
				}
				
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
					
				function getLayoutSettingByArchetypeTypeId(id, settings) {
					var selectedSetting;
					angular.forEach(settings, function(setting) {
						if (setting.archetypeTypeId == id) {
							selectedSetting = setting;
							return false;
						}
					});
					return selectedSetting;
				}
																				
				function reset() {
					scope.graph.getModel().beginUpdate();
					var parent = scope.graph.getDefaultParent();
					scope.graph.view.scale = 1;
					try {
						scope.graph.removeCells(scope.graph.getChildCells(parent));
						var cells = [];
						angular.forEach(scope.classificationBriefInfo.archetypeTypeInfos, function(value, index) {
							var vertex = scope.graph.insertVertex(parent, null, value, 0, 0, cellWidth, 0);

							// Updates the height of the cell (override width
							// for table width is set to 100%)
							scope.graph.updateCellSize(vertex);
							//vertex.geometry.width = cellWidth;
							vertex.geometry.alternateBounds = new mxRectangle(0, 0, cellWidth, 27);
							cells.push(vertex);
						});
						var relations = {};
						angular.forEach(scope.classificationBriefInfo.archetypeTypeRelationshipInfos, function(relationship) {
							var key = relationship.destinationArchetypeTypeId + '-' + relationship.sourceArchetypeTypeId;
							if (relations[key]) {
								relations[key].weight += relationship.weight;
							} else {
								relations[relationship.sourceArchetypeTypeId + '-' + relationship.destinationArchetypeTypeId] = {
									sourceArchetypeTypeId : relationship.sourceArchetypeTypeId,
									destinationArchetypeTypeId : relationship.destinationArchetypeTypeId,
									weight : relationship.weight,
								};
							}
						});
						angular.forEach(relations, function(relationship, key) {
							var sourceCell = getCellById(relationship.sourceArchetypeTypeId, cells);
							var destinationCell = getCellById(relationship.destinationArchetypeTypeId, cells);
							if (sourceCell && destinationCell) {
								var edge = scope.graph.insertEdge(parent, null, relationship.weight, sourceCell, destinationCell, 'strokeWidth=' + relationship.weight / 2);

							}
						});
					} finally {
						// Updates the display
						scope.graph.getModel().endUpdate();
					}
					applyLayout(scope.classificationBriefInfo.layout);
				}

						
				function applyLayout(layout) {
					var model = scope.graph.getModel();
					model.beginUpdate();
					try {

						var parent = scope.graph.getDefaultParent();
						var vertices = scope.graph.getChildVertices(parent);
						angular.forEach(layout, function(setting) {
							var vertex = findVertexById(setting.archetypeTypeId, vertices);
							if (vertex) {
								var geo = model.getGeometry(vertex);
								var dx = new Number(setting.positionX) - geo.x;
								var dy = new Number(setting.positionY) - geo.y;
								scope.graph.moveCells([vertex], dx, dy);
							}
						});
					} finally {
						// Updates the display
						model.endUpdate();
					}
				}
				
				function getCurrentLayout() {
					var parent = scope.graph.getDefaultParent();
					var settings = [];
					angular.forEach(scope.graph.getChildVertices(parent), function(cell) {
						settings.push({
							archetypeTypeId : cell.value.id,
							positionX : cell.geometry.x,
							positionY : cell.geometry.y,
						});
					});
					return settings;
				}

				scope.$watch('classificationBriefInfo', function(classificationBriefInfo) {
					if (classificationBriefInfo) {
						reset();
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

				function getTypeSubTable(archetypeHostInfos, geo) {
					var subTable = "";
					subTable += '<table style="color: black;border:1px solid black;table-layout: fixed;word-wrap: break-word;word-break: break-all;white-space: pre-wrap;text-align: left;" width="' + (geo.width - 9) + '" cellpadding="2">';
					var maxRows = 10;
					angular.forEach(archetypeHostInfos, function(archetypeHostInfo, index) {
						if(index < maxRows){
							subTable += '<tr>' +
											'<td width="' + (geo.width - 1) + '" style="font-size: 10pt;" class="overview-' + archetypeHostInfo.rmEntity.toLowerCase() + '">&nbsp;&nbsp;' + archetypeHostInfo.conceptName + '</td>' + 
										'</tr>';
						} else if (index == maxRows) {
							subTable += '<tr>' +
											'<td width="' + (geo.width - 1) + '"><sapn style="padding-left: 2px;padding-right: 2px;">......</sapn></td>' + 
										'</tr>';
						} else {
						
						}
					});						
					subTable += '</table>';		
					return subTable;
				}
				
				function getLabel(cell){
					var tmp = mxGraph.prototype.getLabel.apply(this, arguments); // "supercall"  
					if (this.getModel().isVertex(cell)) {
						var archetypeTypeName;
						var geo = this.getCellGeometry(cell);
						if (geo) {
							archetypeTypeName = getFixedText(cell.value.name, geo.width - 8, 8, 8);
							var title = '<table style="color: black;border:1px solid black;" width="' + geo.width + '" cellpadding="2" class="title">' + 
											'<tr><th colspan="2" class="text-center" >' + archetypeTypeName + '<sapn class="pull-right">(' + cell.value.archetypeHostInfos.length + ')&nbsp;</span></th></tr>' + 
										'</table>';
							if (this.isCellCollapsed(cell)) {
								temp = title;
							} else {
								temp = title + 
										'<div style="overflow:auto;">' + 
											getTypeSubTable(cell.value.archetypeHostInfos, geo) +
										'</div>';
							}
						}
					} else if (this.getModel().isEdge(cell)) {
						temp = '<span style="background: white;font-size: 10pt;">' + cell.value + '</span>';
					} else {
						temp = '';
					}
					return temp;
				}
			}
		},
	};
}]);

// MxGraph useful examples: graphlayout, codec, layers, scrollbars, tree, userobject
