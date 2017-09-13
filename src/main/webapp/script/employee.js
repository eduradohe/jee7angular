var app = angular.module('employees', ['ngResource', 'ngGrid', 'ui.bootstrap']);

// Create a controller with name employeesListController to bind to the grid section.
app.controller('employeesListController', function ($scope, $rootScope, employeeService) {
    // Initialize required information: sorting, the first page to show and the grid options.
    $scope.sortInfo = {fields: ['id'], directions: ['asc']};
    $scope.employees = {currentPage: 1};
    
    $scope.gridOptions = {
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
        // Broadcasts an event when a row is selected, to signal the form that it needs to load the row data.
        afterSelectionChange: function (rowItem) {
            if (rowItem.selected) {
                $rootScope.$broadcast('employeeSelected', $scope.gridOptions.selectedItems[0].id);
            }
        }
    };

    // Refresh the grid, calling the appropriate rest method.
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

    // Broadcast an event when an element in the grid is deleted. No real deletion is perfomed at this point.
    $scope.deleteRow = function (row) {
        $rootScope.$broadcast('deleteEmployee', row.entity.id);
    };

    // Watch the sortInfo variable. If changes are detected than we need to refresh the grid.
    // This also works for the first page access, since we assign the initial sorting in the initialize section.
    $scope.$watch('sortInfo', function () {
        $scope.employees = {currentPage: 1};
        $scope.refreshGrid();
    }, true);

    // Do something when the grid is sorted.
    // The grid throws the ngGridEventSorted that gets picked up here and assigns the sortInfo to the scope.
    // This will allow to watch the sortInfo in the scope for changed and refresh the grid.
    $scope.$on('ngGridEventSorted', function (event, sortInfo) {
        $scope.sortInfo = sortInfo;
    });

    // Picks the event broadcasted when a employee is saved or deleted to refresh the grid elements with the most
    // updated information.
    $scope.$on('refreshGrid', function () {
        $scope.refreshGrid();
    });

    // Picks the event broadcasted when the form is cleared to also clear the grid selection.
    $scope.$on('clear', function () {
        $scope.gridOptions.selectAll(false);
    });
});

// Create a controller with name employeesFormController to bind to the form section.
app.controller('employeesFormController', function ($scope, $rootScope, $http, employeeService) {
    // Clears the form. Either by clicking the 'Clear' button in the form, or when a successfull save is performed.
    
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
        // Resets the form validation state.
        $scope.employeeForm.$setPristine();
        // Broadcast the event to also clear the grid selection.
        $rootScope.$broadcast('clear');
    };

    // Calls the rest method to save a employee.
    $scope.updateEmployee = function () {
        employeeService.save($scope.employee).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a save message.
                $rootScope.$broadcast('employeeSaved');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    };

    // Picks up the event broadcasted when the employee is selected from the grid and perform the employee load by calling
    // the appropiate rest service.
    $scope.$on('employeeSelected', function (event, id) {
        $scope.employee = employeeService.get({id: id});
    });

    // Picks us the event broadcasted when the employee is deleted from the grid and perform the actual employee delete by
    // calling the appropiate rest service.
    $scope.$on('deleteEmployee', function (event, id) {
        employeeService.delete({id: id}).$promise.then(
            function () {
                // Broadcast the event to refresh the grid.
                $rootScope.$broadcast('refreshGrid');
                // Broadcast the event to display a delete message.
                $rootScope.$broadcast('employeeDeleted');
                $scope.clearForm();
            },
            function () {
                // Broadcast the event for a server error.
                $rootScope.$broadcast('error');
            });
    });
});

// Create a controller with name alertMessagesController to bind to the feedback messages section.
app.controller('alertMessagesController', function ($scope) {
    // Picks up the event to display a saved message.
    $scope.$on('employeeSaved', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record saved successfully!' }
        ];
    });

    // Picks up the event to display a deleted message.
    $scope.$on('employeeDeleted', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Record deleted successfully!' }
        ];
    });

    // Picks up the event to display a server error message.
    $scope.$on('error', function () {
        $scope.alerts = [
            { type: 'danger', msg: 'There was a problem in the server!' }
        ];
    });

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };
});

// Service that provides employees operations
app.factory('employeeService', function ($resource) {
    return $resource('resources/employees/:id');
});