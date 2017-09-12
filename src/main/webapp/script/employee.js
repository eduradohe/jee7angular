var app = angular.module('employees', ['ngGrid', 'ui.bootstrap']);

// Create a controller with name employeesList to bind to the html page.
app.controller('employeesList', function ($scope, employeeService) {
    // Refresh the grid, calling the appropriate service method.
    $scope.refreshGrid = function (page) {
        employeeService.getAll(page, $scope.sortInfo.fields[0], $scope.sortInfo.directions[0]).success(function (data) {
            $scope.employees = data;
        });
    };

    // Do something when the grid is sorted.
    // The grid throws the ngGridEventSorted that gets picked up here and assigns the sortInfo to the scope.
    // This will allow to watch the sortInfo in the scope for changed and refresh the grid.
    $scope.$on('ngGridEventSorted', function (event, sortInfo) {
        $scope.sortInfo = sortInfo;
    });

    // Watch the sortInfo variable. If changes are detected than we need to refresh the grid.
    // This also works for the first page access, since we assign the initial sorting in the initialize section.
    $scope.$watch('sortInfo.fields[0]', function () {
        $scope.refreshGrid($scope.employees.currentPage);
    }, true);

    // Initialize required information: sorting, the first page to show and the grid options.
    $scope.employees = {currentPage: 1};
    $scope.sortInfo = {fields: ['id'], directions: ['asc']};
    $scope.gridOptions = {
        data: 'employees.list',
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

// Service that provides employees operations
app.service('employeeService', function ($http) {
    // Makes the REST request to get the data to populate the grid.
    this.getAll = function (page, sortFields, sortDirections) {
        return $http.get('api/view/employees/listPaginated', {
            params: {
                page: page,
                sortFields: sortFields,
                sortDirections: sortDirections
            }
        });
    }
});