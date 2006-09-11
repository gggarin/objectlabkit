/*
 * $Id: CalendarModifiedFollowingHandler.java 99 2006-09-04 20:30:25Z marchy $
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

import java.util.Calendar;
import java.util.Date;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.Utils;

/**
 * A Jdk <code>Date</code> implementation of the {@link HolidayHandler}, for
 * the <strong>Modified Following</strong> algorithm.
 * 
 * @author Marcin Jekot
 * @author $LastChangedBy: marchy $
 * @version $Revision: 99 $ $Date: 2006-09-04 21:30:25 +0100 (Mon, 04 Sep 2006) $
 * 
 */
public class DateModifiedFollowingHandler implements HolidayHandler<Date> {

    /**
     * If the current date of the give calculator is a non-working day, it will
     * be moved according to the algorithm implemented.
     * 
     * @param calculator
     *            the calculator
     * @return the date which may have moved.
     */
    public Date moveCurrentDate(final DateCalculator<Date> calculator) {
        return move(calculator, 1);
    }

    protected Date move(final DateCalculator<Date> calculator, int step) {
        final Calendar cal = (Calendar) Utils.getCal(calculator.getCurrentBusinessDate()).clone();

        final int month = cal.get(Calendar.MONTH);

        while (calculator.isNonWorkingDay(cal.getTime())) {
            cal.add(Calendar.DAY_OF_MONTH, step);
            if (month != cal.get(Calendar.MONTH)) {
                // switch direction and go back
                step *= -1;
                cal.add(Calendar.DAY_OF_MONTH, step);
            }
        }

        return cal.getTime();
    }

    /**
     * Give the type name for this algorithm.
     * 
     * @return algorithm name.
     */
    public String getType() {
        return HolidayHandlerType.MODIFIED_FOLLLOWING;
    }
}
