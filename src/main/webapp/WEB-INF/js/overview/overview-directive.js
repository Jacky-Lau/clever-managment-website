angular.module('clever.management.directives.overview', []).directive('overview', [
function() {
	return {
		restrict : 'EA',
		scope : {
			archetypeList : '=',
			doubleClick : '='
		},
		templateUrl : 'js/overview/overview.html',
		transclude : true,
		replace : true,
		link : function(scope, element, attrs) {

			// Checks if the browser is supported
			if (!mxClient.isBrowserSupported()) {
				
				// Displays an error message if the browser is not supported.
				mxUtils.error('Browser is not supported!', 200, false);
				
			} else {
				
				var container = element.find('#overview-container').context;
				
				// Creates the graph inside the given container
				var graph = new mxGraph(container);
				
				graph.centerZoom = false;
				
				// Enables HTML markup in all labels
				graph.setHtmlLabels(true);
				
				// Override folding to allow for tables
				graph.isCellFoldable = function(cell, collapse) {
					return this.getModel().isVertex(cell);
				};
				
				// Disables built-in context menu
				//mxEvent.disableContextMenu(container);
				
				// Installs a custom tooltip for cells
				graph.getTooltipForCell = function(cell){
					return cell.value.name;
				};
				
				// Enables tooltips
				graph.setTooltips(true);
				
				// Overrides method to disallow edge label editing
				graph.isCellEditable = function(cell)
				{
					return false;
				};
				
				// Overrides method to provide a cell label in the display
				graph.convertValueToString = function(cell)
				{
					return cell.value.name;
				};
				
				// Installs a handler for double click events in the graph
				// that shows an alert box				
				graph.addListener(mxEvent.DOUBLE_CLICK, function(sender, evt) {
					var cell = evt.getProperty('cell');
					if (cell != null) {
						scope.$apply(function(){
							scope.doubleClick(cell.value);
						});
						
					}
					evt.consume();
				});
				
				// Highlights the vertices when the mouse enters
				// var highlight = new mxCellTracker(graph, '#00FF00');
							
				// Adds zoom buttons in top, left corner
				var buttons = document.createElement('div');
				buttons.style.position = 'absolute';
				buttons.style.overflow = 'visible';

				var bs = graph.getBorderSizes();
				buttons.style.top = (container.offsetTop + bs.y) + 'px';
				buttons.style.left = (container.offsetLeft + bs.x) + 'px';

				var btnLeft = 5;
				var bw = 16;
				var bh = 16;

				if (mxClient.IS_QUIRKS) {
						bw -= 1;
						bh -= 1;
				}

				function addButton(label, funct) {
					var btn = document.createElement('div');
					mxUtils.write(btn, label);
					btn.style.position = 'absolute';
					btn.style.backgroundColor = 'white';
					btn.style.border = '1px solid gray';
					btn.style.textAlign = 'center';
					btn.style.fontSize = '10px';
					btn.style.cursor = 'pointer';
					btn.style.width = bw + 'px';
					btn.style.height = bh + 'px';
					btn.style.left = btnLeft + 'px';
					btn.style.top = '5px';
					btn.style.webkitUserSelect = 'none';

					mxEvent.addListener(btn, 'click', function(evt) {
						funct();
						mxEvent.consume(evt);
					});

					btnLeft += bw + 5;
					buttons.appendChild(btn);
				};

				addButton('+', function() {
					graph.zoomIn();
				});

				addButton('-', function() {
					graph.zoomOut();
				}); 
							

				container.appendChild(buttons);

				// Adds cells to the model in a single step
				var cellWidth = 200;
				var labelWidth = 75;
				// Gets the default parent for inserting new cells. This
				// is normally the first child of the root (ie. layer 0).
				scope.$watch('archetypeList', function(archetypeList) {
					if (archetypeList.length > 0) {
						graph.getModel().beginUpdate();
						var parent = graph.getDefaultParent();
						try {
							angular.forEach(scope.archetypeList, function(value, index) {
								var v = graph.insertVertex(parent, null, value, 0, 0, cellWidth, 0);
								// Updates the height of the cell (override width
								// for table width is set to 100%)
								graph.updateCellSize(v);
								v.geometry.width = cellWidth;
								v.geometry.alternateBounds = new mxRectangle(0, 0, cellWidth, 27);
							});

							// Circle layout, too big
							//var layout = new mxCircleLayout(graph);
							//layout.execute(parent);
							
							// Partition layout, bad
							//var layout = new mxPartitionLayout(graph, true, 10, 20);
							//layout.execute(parent);
							
							// Compact tree layout, not work
							//var layout = new mxCompactTreeLayout(graph);
							//layout.execute(parent);
							
							// Edge label layout, not work
							//var layout = new mxEdgeLabelLayout(graph);
							//layout.execute(parent);
							
							// Stack layout, work, but very simple
							var layout = new mxStackLayout(graph, true, 30);
							layout.wrap = layout.getParentSize(parent).width;
							layout.execute(parent);
							
						} finally {
							// Updates the display
							graph.getModel().endUpdate();
						}
					}
				});
				
				// Overrides getLabel to return empty labels for edges and
				// short markup for collapsed cells.
				graph.getLabel = function(cell) {
					if (this.getModel().isVertex(cell)) {
						
						var archetypeName = cell.value.name;
						var archetypeUse = cell.value.use;
						var archetypePurpose = cell.value.purpose;
						var archetypeKeywords = cell.value.keywords;
						var geo = this.getCellGeometry(cell);
					    if (geo != null)
					    {
					      var max = parseInt(geo.width / 8);			
					      if (archetypeName.length > max)
					      {
					        archetypeName = archetypeName.substring(0, max - 8)+'...';
					      }
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
