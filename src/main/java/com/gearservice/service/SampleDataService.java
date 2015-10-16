package com.gearservice.service;

import com.gearservice.model.cheque.samples.*;

import java.time.OffsetDateTime;
import java.util.Random;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

/**
 * Class SampleData return necessary sample data by request from Enums with sample data
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
public class SampleDataService {
    private static Random random = new Random();

    /**
     * Method getRandomName using method getRandomFrom find one random name from enum and return it
     * @return one random name from Names enum
     */
    public static String getRandomName() {return getRandomFrom(Names.values());}

    /**
     * Method getRandomProduct using method getRandomFrom find one random product from enum and return it
     * @return one random product from Products enum
     */
    public static String getRandomProduct() {return getRandomFrom(Products.values());}

    /**
     * Method getRandomModel using method getRandomFrom find one random model from enum and return it
     * @return one random model from Models enum
     */
    public static String getRandomModel() {return getRandomFrom(Models.values());}

    /**
     * Method getRandomComment using method getRandomFrom find one random comment from enum and return it
     * @return one random comment from Comments enum
     */
    public static String getRandomComment() {return getRandomFrom(Comments.values());}

    /**
     * Method getRandomKit using method getRandomFrom find one random kit from enum and return it
     * @return one random kit from Kits enum
     */
    public static String getRandomKit() {return getRandomFrom(Kits.values());}

    /**
     * Method getRepairPeriod using method randomNumberInRange find one random number in range of 1-99 and return it
     * @return random number in range 1-99
     */
    public static int getRepairPeriod() {return randomNumberInRange(1, 99);}

    /**
     * Method getRandomSerialNumber return one value at current time
     * @return serial number
     */
    public static String getRandomSerialNumber() {return "51000L0RA4T";}

    /**
     * Method getRandomMalfunction return one value at current time
     * @return malfunction
     */
    public static String getRandomMalfunction() {return "Не работает ничего.";}

    /**
     * Method getRandomSpecialNotes return one value at current time
     * @return special notes
     */
    public static String getRandomSpecialNotes() {return "Не работает";}

    /**
     * Method getRandomAddress return one value at current time
     * @return address
     */
    public static String getRandomAddress() {return "г. Донецк, ул. Деловая 15";}

    /**
     * Method getRandomPhone return one value at current time
     * @return phone number
     */
    public static String getRandomPhone() {return "+38 066 666 66 66";}

    /**
     * Method getRandomEmail return one value at current time
     * @return email address
     */
    public static String getRandomEmail() {return "pipe@pipe.ru";}

    /**
     * Method getRandomDate using methods of OffsetDateTime and method randomNumberInRange create random date
     * @return random date
     */
    public static OffsetDateTime getRandomDate() {
        return OffsetDateTime.now()
                .minusYears(randomNumberInRange(1, 50))
                .minusMonths(randomNumberInRange(1, 12))
                .minusDays(randomNumberInRange(1, 30))
                .minusHours(randomNumberInRange(1, 24))
                .minusMinutes(randomNumberInRange(1, 60));
    }

    /**
     * Method getSetConsistFrom create Set of objects that pass to it
     * @param function to create new object for return
     * @return set of objects, that passed to it
     *
     * Sample:
     * Set<String> set = SampleData.getSetConsistFrom(sample -> "sample");
     * assertTrue(set.size() >= 1)
     * assertTrue(set.contains("sample"));
     */
    public static <E> Set<E> getSetConsistFrom(IntFunction<E> function) {
        return IntStream.range(0, randomNumberInRange(1, 5))
                .mapToObj(function)
                .collect(toSet());
    }

    /**
     * Method getRandomFrom return one random value from arrays with any types
     * @param values is array of values, from witch method should return one
     * @param <T> is any type of array that may be passed to method
     * @return one random value from array with specify type
     *
     * Sample:
     * assertThat(Arrays.asList(Models.values()), hasItem(SampleData.getRandomFrom(Models.values())));
     * assertThat(Arrays.asList(Products.values()), hasItem(SampleData.getRandomFrom(Products.values()));
     */
    private static <T> String getRandomFrom(T[] values) {return values[(int)(Math.random() * values.length)].toString();}

    /**
     * Method randomNumberInRange return one int in range 1-99
     * @param min is minimal value in range, from witch int should be create
     * @param max is maximal value in range, from witch int should be create
     * @return int in range of min and max values
     *
     * Sample:
     * SampleValue = SampleData.randomNumberInRange(1, 100);
     * AssertTrue(1 < SampleValue && SampleValue < 100);
     */
    private static int randomNumberInRange(int min, int max) {return random.nextInt((max - min) + 1) + min;}

}
