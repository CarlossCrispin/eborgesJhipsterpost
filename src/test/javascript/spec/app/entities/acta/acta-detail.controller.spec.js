'use strict';

describe('Controller Tests', function() {

    describe('Acta Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockActa, MockTesis, MockAlumno, MockUnidad;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockActa = jasmine.createSpy('MockActa');
            MockTesis = jasmine.createSpy('MockTesis');
            MockAlumno = jasmine.createSpy('MockAlumno');
            MockUnidad = jasmine.createSpy('MockUnidad');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Acta': MockActa,
                'Tesis': MockTesis,
                'Alumno': MockAlumno,
                'Unidad': MockUnidad
            };
            createController = function() {
                $injector.get('$controller')("ActaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'eBorgesApp:actaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
