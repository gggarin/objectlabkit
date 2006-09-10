package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractDateCalculatorCombinationTest;
import net.objectlab.kit.datecalc.common.KitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.WorkingWeek;

import org.joda.time.LocalDate;

public class LocalDateDateCalculatorCombinationTest extends AbstractDateCalculatorCombinationTest<LocalDate> {

    @Override
    protected LocalDate newDate(final String date) {
        return new LocalDate(date);
    }

    @Override
    protected WorkingWeek getWorkingWeek(final WorkingWeek ww) {
        return new JodaWorkingWeek(ww);
    }

    @Override
    protected KitCalculatorsFactory<LocalDate> getDateCalculatorFactory() {
        return LocalDateKitCalculatorsFactory.getDefaultInstance();
    }
}
