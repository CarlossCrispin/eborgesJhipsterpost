'use strict';

describe('Controller Tests', function() {

    describe('Tesis Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTesis, MockAlumno, MockGrado, MockDepartamento, MockUnidad, MockInvestigador, MockTipoAsesor;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTesis = jasmine.createSpy('MockTesis');
            MockAlumno = jasmine.createSpy('MockAlumno');
            MockGrado = jasmine.createSpy('MockGrado');
            MockDepartamento = jasmine.createSpy('MockDepartamento');
            MockUnidad = jasmine.createSpy('MockUnidad');
            MockInvestigador = jasmine.createSpy('MockInvestigador');
            MockTipoAsesor = jasmine.createSpy('MockTipoAsesor');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tesis': MockTesis,
                'Alumno': MockAlumno,
                'Grado': MockGrado,
                'Departamento': MockDepartamento,
                'Unidad': MockUnidad,
                'Investigador': MockInvestigador,
                'TipoAsesor': MockTipoAsesor
            };
            createController = function() {
                $injector.get('$controller')("TesisDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'eBorgesApp:tesisUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
