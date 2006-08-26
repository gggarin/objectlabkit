/*
 * $Id$
 * 
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.objectlab.kit.datecalc.jdk;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.DateCalculatorFactory;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

/**
 * TODO add javadoc 
 *
 * @author Benoit Xhenseval
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 *
 */
public class DefaultDateCalculatorFactory implements DateCalculatorFactory<Date> {
    private static final DateCalculatorFactory DEFAULT = new DefaultDateCalculatorFactory();

    private final ConcurrentMap<String, Set<Date>> holidays = new ConcurrentHashMap<String, Set<Date>>();

    public static DateCalculatorFactory getDefaultInstance() {
        return DEFAULT;
    }

    /**
     * Create a new DateCalculator for a given name and type of handling.
     * 
     * @param name
     *            calendar name (holidays set interested in). If there is set of
     *            holidays with that name, it will return a DateCalculator with
     *            an empty holiday set (will work on Weekend only).
     * @param type
     *            typically one of the value of HolidayHandlerType
     * @return a new DateCalculator
     */
    public DateCalculator<Date> getDateCalculator(final String name, final String holidayHandlerType) {
        final BaseDateCalculator cal = new BaseDateCalculator();
        cal.setName(name);
        if (holidays.containsKey(name)) {
            cal.setNonWorkingDays(holidays.get(name));
        }

        if (HolidayHandlerType.FORWARD.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new ForwardHandler());
        } else {
            throw new UnsupportedOperationException("only forward supported");
        }

        return cal;

        // } else if (HolidayHandlerType.BACKWARD.equals(holidayHandlerType)) {
        // cal.setHolidayHandler(new BackwardHandler());
        // } else if
        // (HolidayHandlerType.MODIFIED_FOLLLOWING.equals(holidayHandlerType)) {
        // cal.setHolidayHandler(new ModifiedFollowingHandler());
        // } else if
        // (HolidayHandlerType.MODIFIED_PRECEEDING.equals(holidayHandlerType)) {
        // cal.setHolidayHandler(new ModifiedPreceedingHandler());
        // }
        // return cal;
    }

    /**
     * Use this method to register a set of holidays for a given calendar, it
     * will replace any existing set. It won't update any existing
     * DateCalculator as these should not be amended whilst in existence (we
     * could otherwise get inconsistent results).
     * 
     * @param name
     *            the calendar name to register these holidays under.
     * @param holidays
     *            the set of holidays (non-working days).
     */
    public void registerHolidays(final String name, final Set<Date> holidays) {
        this.holidays.put(name, holidays);
    }

    private static final PeriodCountCalculator<Date> PCC = new DefaultPeriodCountCalculator();

    public PeriodCountCalculator<Date> getPeriodCountCalculator() {
        return PCC;
    }
}
