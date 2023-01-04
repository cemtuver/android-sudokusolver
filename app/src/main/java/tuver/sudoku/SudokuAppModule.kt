package tuver.sudoku

import tuver.sudoku.di.AppModule
import tuver.sudoku.di.DomainModule
import tuver.sudoku.di.impl.DefaultDomainModule

class SudokuAppModule(
    domainModule: DomainModule = DefaultDomainModule()
) : AppModule,
    DomainModule by domainModule