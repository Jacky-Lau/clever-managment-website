angular.module('clever.management.directives.overview', []).directive('overview', [
function() {
	return {
		restrict : 'EA',
		scope : {
			archetypeList : '=',
			doubleClick : '&',
			animation : '@'
		},
		templateUrl : 'js/overview/overview.html',
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
			
			var animation = scope.animation || 'false';

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
				
				// Disables built-in context menu
				//mxEvent.disableContextMenu(container);
				
				// Enables tooltips
				scope.graph.setTooltips(true);
				
				// Installs a custom tooltip for cells
				scope.graph.getTooltipForCell = function(cell){
					return cell.value.name;
				};
				
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
					layout : new mxCircleLayout(scope.graph)
				};

				// Compact tree layout, not work
				var compactLayout = {
					name : 'Compact tree',
					layout : new mxCompactTreeLayout(scope.graph)
				};

				// Edge label layout, not work
				var edgeLayout = {
					name : 'Edge',
					layout : new mxEdgeLabelLayout(scope.graph)
				};

				// Stack layout, work, but very simple
				var stackLayout = {
					name : 'Stack',
					layout : new mxStackLayout(scope.graph, true, 30, 10, 10)
				};
				
				var organicLayout = {
					name : 'Organic',
					layout : new mxFastOrganicLayout(scope.graph)
				};
				
				stackLayout.layout.wrap = stackLayout.layout.getParentSize(scope.graph.getDefaultParent()).width;

				scope.layouts = [stackLayout, circleLayout, compactLayout, edgeLayout, organicLayout ]; 
				
				function applyLayout(layout){
					scope.graph.getModel().beginUpdate();
					try {
						layout.layout.execute(scope.graph.getDefaultParent());
					} finally {
						// Updates the display
						if (animation == 'true') {
							var morph = new mxMorphing(scope.graph);
							morph.addListener(mxEvent.DONE, function() {
								scope.graph.getModel().endUpdate();
							});
							morph.startAnimation();
						} else {
							scope.graph.getModel().endUpdate();
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
				
				scope.reset = function(){
					scope.graph.getModel().beginUpdate();
						var parent = scope.graph.getDefaultParent();
						scope.graph.view.scale = 1;
						try {
							scope.graph.removeCells(scope.graph.getChildVertices(parent));
							angular.forEach(scope.archetypeList, function(value, index) {
								var vertex = scope.graph.insertVertex(parent, null, value, 0, 0, cellWidth, 0);
								// Updates the height of the cell (override width
								// for table width is set to 100%)
								scope.graph.updateCellSize(vertex);
								vertex.geometry.width = cellWidth;
								vertex.geometry.alternateBounds = new mxRectangle(0, 0, cellWidth, 27);
							});
						} finally {
							// Updates the display
							scope.graph.getModel().endUpdate();
						}
						scope.currentLayout = stackLayout;
				};
				
				// Gets the default parent for inserting new cells. This
				// is normally the first child of the root (ie. layer 0).
				scope.$watch('archetypeList', function(archetypeList) {
					if (archetypeList.length > 0) {
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
				
				// Overrides getLabel to return empty labels for edges and
				// short markup for collapsed cells.
				scope.graph.getLabel = function(cell) {
					if (this.getModel().isVertex(cell)) {

						var archetypeName, archetypeUse, archetypePurpose, archetypeKeywords, archetypeOriginalLanguage;
						var geo = this.getCellGeometry(cell);
						if (geo != null) {
							archetypeName = getFixedText(cell.value.name, geo.width - 8, 8, 8);
							archetypeUse = getFixedText(cell.value.use, geo.width - labelWidth);
							archetypePurpose = getFixedText(cell.value.purpose, geo.width - labelWidth);
							archetypeKeywords = getFixedText(cell.value.keywords, geo.width - labelWidth);
							archetypeOriginalLanguage = getFixedText(cell.value.originalLanguage, geo.width - labelWidth);
						}
					    
						if (this.isCellCollapsed(cell)) {
							return '<table style="color: black;" width="' + (geo.width - 8) + '" border="1" cellpadding="2" class="title">' + 
										'<tr><th colspan="2" class="text-center" >' + archetypeName + '</th></tr>' + 
									'</table>';
						} else {
							return '<table style="color: black;" width="' + (geo.width - 8) + '" border="1" cellpadding="2" class="title">' + 
										'<tr><th colspan="2" class="text-center" >' + archetypeName + '</th></tr>' + 
									'</table>' + 
									'<div style="overflow:auto;cursor:default;">' + 
										'<table width="' + (geo.width - 8) + '" height="100%" border="1" cellpadding="2" class="erd">' + 
											'<tr><td style="width: ' + labelWidth + 'px;">Use: </td><td>' + archetypeUse + '</td></tr>' + 
											'<tr><td style="width: ' + labelWidth + 'px;">Purpose: </td><td>' + archetypePurpose + '</td></tr>' +
											'<tr><td style="width: ' + labelWidth + 'px;">Keywords: </td><td>' + archetypeKeywords + '</td></tr>' + 
											'<tr><td style="width: ' + labelWidth + 'px;">Original Language: </td><td>' + archetypeOriginalLanguage + '</td></tr>' + 
										'</table>' +
									'</div>';
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
