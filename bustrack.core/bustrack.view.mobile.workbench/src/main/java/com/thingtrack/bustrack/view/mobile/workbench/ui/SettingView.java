package com.thingtrack.bustrack.view.mobile.workbench.ui;

import com.thingtrack.bustrack.view.mobile.workbench.ContextApp;
import com.thingtrack.bustrack.view.mobile.workbench.ContextServices;
import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.NumberField;
import com.vaadin.addon.touchkit.ui.Switch;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Reindeer;

public class SettingView extends NavigationView {
	private static final long serialVersionUID = 1L;
	
	private Label separator = new Label("<hr/>", Label.CONTENT_XHTML);
	
	@SuppressWarnings("unused")
	private BustrackHierarchyManager nav;
	
    @SuppressWarnings("serial")
	public SettingView(final BustrackHierarchyManager nav) {
        setCaption("Configuración");
        setWidth("100%");
        setHeight("100%");
        
        this.nav = nav;
    	
        Button logout = new Button("Salir");
        logout.addListener(new ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				nav.navigateBack();
				
			}
		});
        
        setLeftComponent(logout);
        
    }
    
    @Override
    public void attach() {
        super.attach();
       
        // default View Layout
        CssLayout content = new CssLayout();
        content.setWidth("100%");
        
        // Build Settings
        if (ContextApp.getSensorLocation() != null) {  
        		content.addComponent(LocalizationSettings());        
        		content.addComponent(separator);    
        }
        
        if (ContextApp.getSensorTelemetry() != null) {
        	content.addComponent(TelemetrySettings());
        	content.addComponent(separator);
        }
        
        if (ContextApp.getSensorMessage() != null)
        	content.addComponent(MessageSettings());
        
        setContent(content);
    }
        
    private VerticalComponentGroup LocalizationSettings() {
        VerticalComponentGroup componentLocalizationGroup = new VerticalComponentGroup();
        componentLocalizationGroup.setCaption("Sobre Localización");
        
        // Help Localization Parameter
        Label localizationAbout = new Label(
                  "<div style='color:#333;'><p>Parámetros configuración Localización:</p>"
        		+ "<p><strong>Activo:</strong> estado del servicio"
                + "<p><strong>Min Distancia:</strong> intervalo de distancia mínima para las notificaciones en metros"
                + "<p><strong>Min Tiempo:</strong> intervalo de tiempo mínimo para las notificaciones en segundos"
                + "</div>", Label.CONTENT_XHTML);

        componentLocalizationGroup.addComponent(localizationAbout);
        
        // active parameter
        Switch localizationActive = new Switch("Activo:");
        if (ContextApp.getSensorLocation().getSensorStatus().getCode().equals("ACTIVE"))
        	localizationActive.setValue(true);
        else
        	localizationActive.setValue(false);
        componentLocalizationGroup.addComponent(localizationActive);
        
        localizationActive.setImmediate(true);
        
        localizationActive.addListener(new Property.ValueChangeListener() {			
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {					
						try {
							if ((Boolean) event.getProperty().getValue())
								ContextServices.getSensorLocationService().saveStatus(ContextApp.getSensorLocation(), "ACTIVE");
							else
								ContextServices.getSensorLocationService().saveStatus(ContextApp.getSensorLocation(), "UNACTIVE");									
						} catch (Exception e) {							
							e.printStackTrace();
						}
					}
					
				}
							
		});
        
        // min Distance parameter
        NumberField minDistance = new NumberField("Min Distancia [m]:");
        minDistance.setWidth("100%");
        minDistance.setValue(String.valueOf(ContextApp.getSensorLocation().getMinDistance()));
        minDistance.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue() != null) {
                	ContextApp.getSensorLocation().setMinDistance(((Double) event.getProperty().getValue()).floatValue());
                	
                	try {
						ContextServices.getSensorLocationService().save(ContextApp.getSensorLocation());
					} catch (Exception e) {						
						e.printStackTrace();
						
						return;
					}
                }

            }
        });
        componentLocalizationGroup.addComponent(minDistance);
        
        // min Time parameter
        NumberField minTime = new NumberField("Min Tiempo [s]:");
        minTime.setWidth("100%");
        minTime.setValue(String.valueOf(ContextApp.getSensorLocation().getMinTime()));
        minTime.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue() != null) {
                	ContextApp.getSensorLocation().setMinTime((Long) event.getProperty().getValue());
                	try {
						ContextServices.getSensorLocationService().save(ContextApp.getSensorLocation());
					} catch (Exception e) {						
						e.printStackTrace();
						
						return;
					}
                }

            }
        });
        componentLocalizationGroup.addComponent(minTime);
        
        return componentLocalizationGroup;
    }
    
    private VerticalComponentGroup TelemetrySettings() {
        VerticalComponentGroup componentTelemetryGroup = new VerticalComponentGroup();
        componentTelemetryGroup.setCaption("Sobre Telemetría");
        
        Label telemetryAbout = new Label(
                  "<div style='color:#333;'><p>Parámetros configuración Telemetría:</p>"
        		+ "<p><strong>Activo:</strong> estado delservicio"
                + "</div>", Label.CONTENT_XHTML);

        componentTelemetryGroup.addComponent(telemetryAbout);
        
        // active parameter
        Switch telemetryActive = new Switch("Activo:");
        if (ContextApp.getSensorTelemetry().getSensorStatus().getCode().equals("ACTIVE"))
        	telemetryActive.setValue(true);
        else
        	telemetryActive.setValue(false);
        
        telemetryActive.setImmediate(true);
        
        telemetryActive.addListener(new Property.ValueChangeListener() {			
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {					
						try {
							if ((Boolean) event.getProperty().getValue())
								ContextServices.getSensorTelemetryService().saveStatus(ContextApp.getSensorTelemetry(), "ACTIVE");
							else
								ContextServices.getSensorTelemetryService().saveStatus(ContextApp.getSensorTelemetry(), "UNACTIVE");									
						} catch (Exception e) {							
							e.printStackTrace();
						}
					}
					
				}
							
		});
        
        componentTelemetryGroup.addComponent(telemetryActive);
        
        return componentTelemetryGroup;
    }
    
    private VerticalComponentGroup MessageSettings() {
        VerticalComponentGroup componentMessageGroup = new VerticalComponentGroup();
        componentMessageGroup.setCaption("Sobre Mesajería");
        
        Label messageAbout = new Label(
                  "<div style='color:#333;'><p>Parámetros configuración Mensajería:</p>"
        		+ "<p><strong>Activo:</strong> estado del servicio"
        		+ "<p><strong>Servidor:</strong> nombre del servidor mensajería"
        		+ "<p><strong>Puerto:</strong> puerto del servidor mensajería"
        		+ "<p><strong>Canal:</strong> nombre del canel de comunica del servidor mensajería"
        		+ "<p><strong>Periodo actividad:</strong> periodo actividad en milisegundos"
        		+ "<p><strong>Nivel calidad:</strong> nivel calidad mensajería"
                + "</div>", Label.CONTENT_XHTML);

        componentMessageGroup.addComponent(messageAbout);
        
        // active parameter
        Switch messageActive = new Switch("Activo:");
        if (ContextApp.getSensorMessage().getSensorStatus().getCode().equals("ACTIVE"))
        	messageActive.setValue(true);
        else
        	messageActive.setValue(false);
         
        messageActive.setImmediate(true);
        
        messageActive.addListener(new Property.ValueChangeListener() {			
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty().getValue() != null) {					
						try {
							if ((Boolean) event.getProperty().getValue())
								ContextServices.getSensorMessageService().saveStatus(ContextApp.getSensorMessage(), "ACTIVE");
							else
								ContextServices.getSensorMessageService().saveStatus(ContextApp.getSensorMessage(), "UNACTIVE");									
						} catch (Exception e) {							
							e.printStackTrace();
						}
					}
					
				}
							
		});
        
        componentMessageGroup.addComponent(messageActive);
        
        // host parameter
        TextField host = new TextField("Servidor:");
        host.setWidth("100%");
        host.setValue(ContextApp.getSensorMessage().getMessageBrokerHost());
        host.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue() != null) {
                    if (event.getProperty().getValue() != null) {
                    	ContextApp.getSensorMessage().setMessageBrokerHost(event.getProperty().getValue().toString());
                    	
                    	try {
    						ContextServices.getSensorMessageService().save(ContextApp.getSensorMessage());
    					} catch (Exception e) {						
    						e.printStackTrace();
    						
    						return;
    					}
                    }
                }

            }
        });
        componentMessageGroup.addComponent(host);
        
        // port host parameter
        NumberField port = new NumberField("Puerto:");
        port.setWidth("100%");
        port.setValue(String.valueOf(ContextApp.getSensorMessage().getMessageBrokerPort()));
        port.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue() != null) {
                    if (event.getProperty().getValue() != null) {
                    	ContextApp.getSensorMessage().setMessageBrokerPort((Integer)(event.getProperty().getValue()));
                    	
                    	try {
    						ContextServices.getSensorMessageService().save(ContextApp.getSensorMessage());
    					} catch (Exception e) {						
    						e.printStackTrace();
    						
    						return;
    					}
                    }
                }

            }
        });
        componentMessageGroup.addComponent(port);
                
        // Topic host parameter
        TextField topic = new TextField("Canal:");
        topic.setWidth("100%");
        topic.setValue(ContextApp.getSensorMessage().getTopic());
        topic.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue() != null) {
                    if (event.getProperty().getValue() != null) {
                    	ContextApp.getSensorMessage().setTopic(event.getProperty().getValue().toString());
                    	
                    	try {
    						ContextServices.getSensorMessageService().save(ContextApp.getSensorMessage());
    					} catch (Exception e) {						
    						e.printStackTrace();
    						
    						return;
    					}
                    }
                }

            }
        });
        componentMessageGroup.addComponent(topic);
                
        // keepalive host parameter
        NumberField keepAlive = new NumberField("Periodo actividad (ms):");
        keepAlive.setWidth("100%");
        keepAlive.setValue(String.valueOf(ContextApp.getSensorMessage().getKeepAlive()));
        keepAlive.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
	               if (event.getProperty().getValue() != null) {
	                    if (event.getProperty().getValue() != null) {
	                    	ContextApp.getSensorMessage().setKeepAlive((Integer)event.getProperty().getValue());
	                    	
	                    	try {
	    						ContextServices.getSensorMessageService().save(ContextApp.getSensorMessage());
	    					} catch (Exception e) {						
	    						e.printStackTrace();
	    						
	    						return;
	    					}
	                    }
	                }

            }
        });
        componentMessageGroup.addComponent(keepAlive);
        
        // QualityofService host parameter
        OptionGroup qualityOfService = new OptionGroup();
        Label qualityOfServiceHeader = new Label("Calidad Servicio");
        qualityOfServiceHeader.setStyleName(Reindeer.LABEL_H2);
        componentMessageGroup.addComponent(qualityOfServiceHeader);
        
        qualityOfService.addItem("Nivel 0");
        qualityOfService.addItem("Nivel 1");
        qualityOfService.addItem("Nivel 2");
        
        if (ContextApp.getSensorMessage().getQualityOfService() == 0)
        	qualityOfService.select("Nivel 0");
        else if (ContextApp.getSensorMessage().getQualityOfService() == 1)
        	qualityOfService.select("Nivel 1");
        else
        	qualityOfService.select("Nivel 2");
        
        qualityOfService.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue().toString() == "Nivel 0")
                	ContextApp.getSensorMessage().setQualityOfService(0);
                else if (event.getProperty().getValue().toString() == "Nivel 1")
                	ContextApp.getSensorMessage().setQualityOfService(1);
                else
                	ContextApp.getSensorMessage().setQualityOfService(2);
                
            	try {
					ContextServices.getSensorMessageService().save(ContextApp.getSensorMessage());
				} catch (Exception e) {						
					e.printStackTrace();
					
					return;
				}                
            }
        });
        qualityOfService.setImmediate(true);
        componentMessageGroup.addComponent(qualityOfService);
        
        return componentMessageGroup;
    }
}
