/*
 * This file is generated by jOOQ.
 */
package nl.jnext.workshop.testcontainers.vakantieplanner.jooq.tables.records;


import java.time.LocalDate;

import nl.jnext.workshop.testcontainers.vakantieplanner.jooq.tables.Holiday;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HolidayRecord extends UpdatableRecordImpl<HolidayRecord> implements Record3<Integer, LocalDate, LocalDate> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.holiday.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.holiday.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.holiday.start_date</code>.
     */
    public void setStartDate(LocalDate value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.holiday.start_date</code>.
     */
    public LocalDate getStartDate() {
        return (LocalDate) get(1);
    }

    /**
     * Setter for <code>public.holiday.end_date</code>.
     */
    public void setEndDate(LocalDate value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.holiday.end_date</code>.
     */
    public LocalDate getEndDate() {
        return (LocalDate) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, LocalDate, LocalDate> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<Integer, LocalDate, LocalDate> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Holiday.HOLIDAY.ID;
    }

    @Override
    public Field<LocalDate> field2() {
        return Holiday.HOLIDAY.START_DATE;
    }

    @Override
    public Field<LocalDate> field3() {
        return Holiday.HOLIDAY.END_DATE;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public LocalDate component2() {
        return getStartDate();
    }

    @Override
    public LocalDate component3() {
        return getEndDate();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public LocalDate value2() {
        return getStartDate();
    }

    @Override
    public LocalDate value3() {
        return getEndDate();
    }

    @Override
    public HolidayRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public HolidayRecord value2(LocalDate value) {
        setStartDate(value);
        return this;
    }

    @Override
    public HolidayRecord value3(LocalDate value) {
        setEndDate(value);
        return this;
    }

    @Override
    public HolidayRecord values(Integer value1, LocalDate value2, LocalDate value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached HolidayRecord
     */
    public HolidayRecord() {
        super(Holiday.HOLIDAY);
    }

    /**
     * Create a detached, initialised HolidayRecord
     */
    public HolidayRecord(Integer id, LocalDate startDate, LocalDate endDate) {
        super(Holiday.HOLIDAY);

        setId(id);
        setStartDate(startDate);
        setEndDate(endDate);
    }
}
