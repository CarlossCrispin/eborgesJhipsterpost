'use strict';

describe('Controller Tests', function() {

    describe('Alumno Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAlumno, MockDepartamento, MockGrado, MockGenero;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAlumno = jasmine.createSpy('MockAlumno');
            MockDepartamento = jasmine.createSpy('MockDepartamento');
            MockGrado = jasmine.createSpy('MockGrado');
            MockGenero = jasmine.createSpy('MockGenero');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Alumno': MockAlumno,
                'Departamento': MockDepartamento,
                'Grado': MockGrado,
                'Genero': MockGenero
            };
            createController = function() {
                $injector.get('$controller')("AlumnoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'eBorgesApp:alumnoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
