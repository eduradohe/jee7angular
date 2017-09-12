var app = angular.module('employees', ['ngGrid', 'ui.bootstrap']);

app.controller('employeesList', function ($scope, $http) {
	
	$scope.refreshGrid = function () {
		$http({
			url: 'services/employees',
			method: 'GET'
		}).success(function (data) {
			$scope.employees = data;
		});
	};
	
    $scope.$on('ngGridEventSorted', function (event, sortInfo) {
        $scope.sortInfo = sortInfo;
    });
	
	$scope.$watch('sortInfo', function() {
		$scope.refreshGrid();
	}, true);
	
	$scope.sortInfo = {fields: ['id'], directions: ['asc']};
	$scope.employees = null;
    $scope.gridOptions = {
    		data: 'employees',
            useExternalSorting: true,
            sortInfo: $scope.sortInfo,
            columnDefs: [
            	{field: 'id', displayName: 'ID'},
            	{field: 'name', displayName: 'Name'},
            	{field: 'income', displayName: 'Income'},
            	{field: 'department.name', displayName: 'Department'}
            ]
    };
});