package org.example.jewelryshop.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.BaseColor;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import org.example.jewelryshop.JewelryShop;
import org.example.jewelryshop.models.*;
import org.example.jewelryshop.services.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class Manager {

    public static Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    public static Stage currentStage;
    public static Stage lastStage;
    public static Stage editStage;
    public static Stage secondStage;
    public static Stage mainStage;
    public static workers currentWorker = new workers();
    public static products currentProduct = new products();
    public static gemstones currentGemstone = new gemstones();
    public static orders currentOrder = new orders();
    public static orderProducts currentOrderProducts = new orderProducts();
    public static customers currentCustomer = new customers();
    public static metals currentMetal= new metals();
    public static deliveryMethods currentDeliveryMethods = new deliveryMethods();

    public static CustomerServices customerServices = new CustomerServices();
    public static ProductServices productServices = new ProductServices();
    public static GemstoneServices gemstoneServices = new GemstoneServices();
    public static MetalServices metalServices = new MetalServices();
    public static WorkerServices workerServices = new WorkerServices();
    public static OrderServices orderServices = new OrderServices();
    public static RingSizeServices ringSizeServices = new RingSizeServices();
    public static DeliveryMethodServices deliveryMethodServices = new DeliveryMethodServices();
    public static StatusServices statusServices = new StatusServices();
    public static OrderProductsService orderProductsService = new OrderProductsService();

    public Manager() throws IOException {
    }

    public static void openLoginStage(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JewelryShop.class.getResource("cus-or-wor-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("base-styles1.css");
        stage.getIcons().add(new Image(JewelryShop.class.getResourceAsStream("start.png")));
        stage.setTitle("Ювелирный магазин");
        stage.setScene(scene);
        stage.show();
        Manager.currentStage = stage;
    }

    public static void LoadSecondStageScene(String fxmlFileName, String title)
    {
        FXMLLoader fxmlLoader = new FXMLLoader(JewelryShop.class.getResource(fxmlFileName));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), screenSize.getWidth(), screenSize.getHeight()- 50);
            scene.getStylesheets().add("base-styles.css");
            Manager.secondStage.setScene(scene);
            Manager.secondStage.setMaximized(true);
            Manager.secondStage.setTitle(title);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void WarningMessage() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Закрыть окно");
        alert.setHeaderText("Вы хотите закрыть текущее окно?");
        alert.setContentText("Все несохраненные данные, будут утеряны! ");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            editStage.close();
        }
    }

    public static void MessageBox(String title,String header,String content,Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public static void ErrorosMessageBox(String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Внимание!");
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void ShowWindow(String source,String title) throws IOException {
//        if (currentStage != null)
//            currentStage.hide();
        if (currentStage != null) {
            currentStage.close();
        }
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(JewelryShop.class.getResource(source));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("base-styles.css");
        Stage stage = new Stage();
        stage.getIcons().add(new Image(JewelryShop.class.getResourceAsStream("start.png")));
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        lastStage = currentStage;
        currentStage = stage;
        stage.setOnCloseRequest(e -> {
            if(lastStage == null) {
                try {
                    openLoginStage(new Stage());
                    return;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            lastStage.show();
            currentStage.close();
            currentStage = lastStage;
            lastStage = null;
        });
    }

    public static void ShowWindow1(String source,String title) throws IOException {
//        if (currentStage != null)
//            currentStage.hide();
        if (currentStage != null) {
            currentStage.close();
        }
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(JewelryShop.class.getResource(source));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("base-styles1.css");
        Stage stage = new Stage();
        stage.getIcons().add(new Image(JewelryShop.class.getResourceAsStream("start.png")));
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        lastStage = currentStage;
        currentStage = stage;
        stage.setOnCloseRequest(e -> {
            if(lastStage == null) {
                try {
                    openLoginStage(new Stage());
                    return;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            lastStage.show();
            currentStage.close();
            currentStage = lastStage;
            lastStage = null;
        });
    }

    public static void ShowEditWindow(String source,String title) throws IOException {
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(JewelryShop.class.getResource(source));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add("base-styles.css");
        Stage stage = new Stage();
        stage.getIcons().add(new Image(JewelryShop.class.getResourceAsStream("start.png")));
        stage.setTitle(title);
        stage.setScene(scene);
        editStage = stage;
        stage.showAndWait();
        stage.setOnCloseRequest(e -> {
            WarningMessage();
        });
    }

    public static void PrintOrdersToPDFAll(List<orders> ordersList) throws FileNotFoundException, DocumentException {
        String FONT = "src/main/resources/fonts/arial.ttf";

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.PDF)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(mainStage);

        if (file != null) {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            Font font = FontFactory.getFont(FONT, "cp1251", BaseFont.EMBEDDED, 12);
            document.open();

            for (orders order : ordersList) {
                document.add(new Paragraph("Заказ №: " + order.getOrderId(), font));
                if (order.getCustomer() != null) {
                    document.add(new Paragraph("На имя: " + order.getCustomer().getFullName(), font));
                    document.add(new Paragraph("Телефон: " + order.getCustomer().getPhone(), font));  // добавлено поле телефона
                }
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate createDate = order.getCreateDate();
                document.add(new Paragraph("Дата заказа: " + createDate.format(dateFormatter), font));
                document.add(new Paragraph("Статус: " + (order.getStatus() != null ? order.getStatus().getTitle() : ""), font));
                document.add(new Paragraph("Способ доставки: " + (order.getDeliveryMethod() != null ? order.getDeliveryMethod().getTitle() : ""), font));
                document.add(new Paragraph("Адрес доставки: " + (order.getDeliveryAddress() != null ? order.getDeliveryAddress() : ""), font));
                document.add(Chunk.NEWLINE);

                PdfPTable table = new PdfPTable(new float[]{10, 40, 15, 15, 10, 15});
                table.setWidthPercentage(100);

                String[] headers = {"№", "Наименование товара", "Количество", "Цена за шт.", "Размер кольца", "Итого"};
                for (String headerTitle : headers) {
                    PdfPCell header = new PdfPCell(new Phrase(headerTitle, font));
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    table.addCell(header);
                }

                table.addCell("1");
                table.addCell(order.getProduct() != null ? order.getProduct().getTitle() : "");
                table.addCell(String.valueOf(order.getQuantity()));
                double cost = order.getProduct() != null ? order.getProduct().getCost() : 0.0;
                table.addCell(String.format("%.2f", cost));
                table.addCell(order.getRingSize() != null ? order.getRingSize().getSize() : "");
                double total = cost * order.getQuantity();
                table.addCell(String.format("%.2f", total));

                document.add(table);

                document.add(Chunk.NEWLINE);
                document.add(new Paragraph("Общая стоимость заказа (с учетом доставки): " + String.format("%.2f руб.", (double) order.getFullCost()), font));

                document.add(Chunk.NEWLINE);
                document.add(new LineSeparator());
                document.add(Chunk.NEWLINE);
            }

            document.close();
        }
    }

    public static void PrintOrdersToPDF(orders order) throws FileNotFoundException, DocumentException {
        String FONT = "src/main/resources/fonts/arial.ttf";

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.PDF)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(mainStage);

        if (file != null) {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            Font font = FontFactory.getFont(FONT, "cp1251", BaseFont.EMBEDDED, 12);
            document.open();

            document.add(new Paragraph("Заказ №: " + order.getOrderId(), font));
            if (order.getCustomer() != null) {
                document.add(new Paragraph("На имя: " + order.getCustomer().getFullName(), font));
                document.add(new Paragraph("Телефон: " + order.getCustomer().getPhone(), font));  // добавлено поле телефона
            }
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate createDate = order.getCreateDate();
            document.add(new Paragraph("Дата заказа: " + createDate.format(dateFormatter), font));
            document.add(new Paragraph("Статус: " + (order.getStatus() != null ? order.getStatus().getTitle() : ""), font));
            document.add(new Paragraph("Способ доставки: " + (order.getDeliveryMethod() != null ? order.getDeliveryMethod().getTitle() : ""), font));
            document.add(new Paragraph("Адрес доставки: " + (order.getDeliveryAddress() != null ? order.getDeliveryAddress() : ""), font));
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(new float[]{10, 40, 15, 15, 10, 15});
            table.setWidthPercentage(100);

            String[] headers = {"№", "Наименование товара", "Количество", "Цена за шт.", "Размер кольца", "Итого"};
            for (String headerTitle : headers) {
                PdfPCell header = new PdfPCell(new Phrase(headerTitle, font));
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(2);
                table.addCell(header);
            }

            table.addCell("1");
            table.addCell(order.getProduct() != null ? order.getProduct().getTitle() : "");
            table.addCell(String.valueOf(order.getQuantity()));
            double cost = order.getProduct() != null ? order.getProduct().getCost() : 0.0;
            table.addCell(String.format("%.2f", cost));
            table.addCell(order.getRingSize() != null ? order.getRingSize().getSize() : "");
            double total = cost * order.getQuantity();
            table.addCell(String.format("%.2f", total));

            document.add(table);

            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Общая стоимость заказа (с учетом доставки): " + String.format("%.2f руб.", (double) order.getFullCost()), font));

            document.add(Chunk.NEWLINE);
            document.add(new LineSeparator());
            document.add(Chunk.NEWLINE);

            document.close();
        }
    }
}