package com.assignment.toolrentalapplication;

import java.util.Calendar;
import java.util.Date;

public class Checkout {
    private Tool tool;
    private int rentalDays;
    private int discountPercent;
    private Date checkoutDate;

    public Checkout(Tool tool, int rentalDays, int discountPercent, Date checkoutDate) {
        this.tool = tool;
        this.rentalDays = rentalDays;
        this.discountPercent = discountPercent;
        this.checkoutDate = checkoutDate;
    }

    public RentalAgreement generateRentalAgreement() throws Exception {
        // Calculate due date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkoutDate);
        calendar.add(Calendar.DAY_OF_MONTH, rentalDays);
        Date dueDate = calendar.getTime();

        // Calculate charge days
        int chargeDays = calculateChargeDays(checkoutDate, dueDate);

        // Calculate pre-discount charge
        double preDiscountCharge = chargeDays * tool.getDailyCharge();

        // Calculate discount amount
        double discountAmount = (preDiscountCharge * discountPercent) / 100.0;

        // Calculate final charge
        double finalCharge = preDiscountCharge - discountAmount;

        // Create and return RentalAgreement instance
        return new RentalAgreement(tool.getToolCode(), tool.getToolType(), tool.getBrand(),
                rentalDays, checkoutDate, dueDate, tool.getDailyCharge(),
                chargeDays, preDiscountCharge, discountPercent, discountAmount, finalCharge);
    }

    private int calculateChargeDays(Date startDate, Date endDate) {
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        int chargeDays = 0;

        while (!start.after(end)) {
            int dayOfWeek = start.get(Calendar.DAY_OF_WEEK);
            boolean isWeekend = (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
            boolean isHoliday = isHoliday(start);

            if (!isWeekend || tool.isWeekendCharge()) {
                if (!isHoliday || tool.isHolidayCharge()) {
                    chargeDays++;
                }
            }

            start.add(Calendar.DAY_OF_MONTH, 1);
        }

        return chargeDays;
    }

    private boolean isHoliday(Calendar date) {
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);

        if (month == Calendar.JULY && day == 4) {
            return true; // Independence Day
        } else if (month == Calendar.SEPTEMBER && date.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && (day >= 1 && day <= 7)) {
            return true; // Labor Day
        }

        return false;
    }
}
