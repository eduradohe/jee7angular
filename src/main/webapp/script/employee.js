var app = angular.module('employees', ['ngResource', 'ngGrid', 'ui.bootstrap']);

app.controller('employeesListController', function ($scope, $rootScope, employeeService) {
    $scope.sortInfo = {fields: ['id'], directions: ['asc']};
    $scope.employees = {currentPage: 1};
    
    $scope.employeesGrid = {
        data: 'employees.list',
        useExternalSorting: true,
        sortInfo: $scope.sortInfo,

        columnDefs: [
            { field: 'id', displayName: 'ID' },
            { field: 'name', displayName: 'Name' },
            { field: 'income', displayName: 'Income' },
            { field: 'department.name', displayName: 'Department' },
            { field: '', width: 30, cellTemplate: '<span class="glyphicon glyphicon-remove remove" ng-click="deleteRow(row)"></span>' }
        ],

        multiSelect: false,
        selectedItems: [],
        
        afterSelectionChange: function (rowItem) {
            if (rowItem.selected) {
                $rootScope.$broadcast('employeeSelected', $scope.employeesGrid.selectedItems[0].id);
            }
        }
    };

    $scope.refreshGrid = function () {
        var listEmployeesArgs = {
            page: $scope.employees.currentPage,
            sortFields: $scope.sortInfo.fields[0],
            sortDirections: $scope.sortInfo.directions[0]
        };
        
        employeeService.get(listEmployeesArgs, function (data) {
            $scope.employees = data;
        });
        
        $rootScope.$broadcast('reloaded');
    };

    $scope.deleteRow = function (row) {
        $rootScope.$broadcast('deleteEmployee', row.entity.id);
    };

    $scope.$watch('sortInfo', function () {
        $scope.employees = {currentPage: 1};
        $scope.refreshGrid();
    }, true);

    $scope.$on('ngGridEventSorted', function (event, sortInfo) {
        $scope.sortInfo = sortInfo;
    });

    $scope.$on('refreshGrid', function () {
        $scope.refreshGrid();
    });

    $scope.$on('clear', function () {
        $scope.employeesGrid.selectAll(false);
    });
});

app.controller('employeesFormController', function ($scope, $rootScope, $http, employeeService) {
    
	$scope.$on('reloaded', function () {
		$http({
			url: 'resources/departments/list',
			method: 'GET'
		}).success(function (data) {
			$scope.comboDepartments = data;
		});
	});
	
    $scope.clearForm = function () {
        $scope.employee = null;
        $scope.employeeForm.$setPristine();
        
        $rootScope.$broadcast('clear');
    };

    $scope.updateEmployee = function () {
        employeeService.save($scope.employee).$promise.then(
            function () {
                $rootScope.$broadcast('refreshGrid');
                $rootScope.$broadcast('employeeSaved');
                
                $scope.clearForm();
            },
            function () {
                $rootScope.$broadcast('error');
            });
    };

    $scope.$on('employeeSelected', function (event, id) {
        $scope.employee = employeeService.get({id: id});
    });

    $scope.$on('deleteEmployee', function (event, id) {
        employeeService.delete({id: id}).$promise.then(
            function () {
                $rootScope.$broadcast('refreshGrid');
                $rootScope.$broadcast('employeeDeleted');
                
                $scope.clearForm();
            },
            function () {
                $rootScope.$broadcast('error');
            }
        );
    });
});

app.controller('alertMessagesController', function ($scope) {
	
    $scope.$on('employeeSaved', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record saved successfully!' }
        ];
    });

    $scope.$on('employeeDeleted', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record deleted successfully!' }
        ];
    });

    $scope.$on('error', function () {
        $scope.alerts = [
            { type: 'danger', msg: 'There was a problem in the server!' }
        ];
    });

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };
});

app.factory('employeeService', function ($resource) {
    return $resource('resources/employees/:id');
});

app.controller('departmentsListController', function ($scope, $rootScope, departmentService) {
    $scope.sortInfo = {fields: ['id'], directions: ['asc']};
    $scope.departments = {currentPage: 1};
    
    $scope.departmentsGrid = {
        data: 'departments.list',
        useExternalSorting: true,
        sortInfo: $scope.sortInfo,
        
        multiSelect: false,
        selectedItems: [],
        
        columnDefs: [
            { field: 'id', displayName: 'ID' },
            { field: 'name', displayName: 'Name' },
            { field: 'budget', displayName: 'Budget' },
            { field: '', width: 30, cellTemplate: '<span class="glyphicon glyphicon-remove remove" ng-click="deleteRow(row)"></span>' }
        ]
    };

    $scope.refreshDepartmentsGrid = function () {
        var listDepartmentsArgs = {
            page: $scope.departments.currentPage,
            sortFields: $scope.sortInfo.fields[0],
            sortDirections: $scope.sortInfo.directions[0]
        };
        
        departmentService.get(listDepartmentsArgs, function (data) {
            $scope.departments = data;
        });
    };

    $scope.deleteRow = function (row) {
        departmentService.delete({id: row.entity.id}).$promise.then(
            function () {
                $scope.$broadcast('refreshDepartmentsGrid');
                $rootScope.$broadcast('departmentDeleted');
                
                $scope.clearForm();
            },
            function () {
                $rootScope.$broadcast('error');
            }
        );
    };

    $scope.$watch('sortInfo', function () {
        $scope.departments = {currentPage: 1};
        $scope.refreshDepartmentsGrid();
    }, true);
    
    $scope.$on('employeeSaved', function() {
    	$scope.refreshDepartmentsGrid();
    });
    
    $scope.$on('ngGridEventSorted', function (event, sortInfo) {
        $scope.sortInfo = sortInfo;
    });

    $scope.$on('refreshDepartmentsGrid', function () {
        $scope.refreshDepartmentsGrid();
    });

    $scope.$on('clear', function () {
        $scope.departmentsGrid.selectAll(false);
    });
});

app.factory('departmentService', function ($resource) {
	return $resource('resources/departments/:id');
});