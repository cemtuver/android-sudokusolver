package tuver.sudokusolver

import tuver.sudokusolver.di.AppModule
import tuver.sudokusolver.di.DomainModule
import tuver.sudokusolver.di.impl.DefaultDomainModule

class SudokuSolverAppModule(
    domainModule: DomainModule = DefaultDomainModule()
) : AppModule,
    DomainModule by domainModule