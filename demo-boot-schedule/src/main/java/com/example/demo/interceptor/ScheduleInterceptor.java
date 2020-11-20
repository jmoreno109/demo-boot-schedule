package com.example.demo.interceptor;

import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component("ScheduleInterceptor")
public class ScheduleInterceptor implements HandlerInterceptor {

	@Value("${config.schedule.open}")
	private Integer open;

	@Value("${config.schedule.close}")
	private Integer close;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		LocalTime time = LocalTime.now();
		Integer hour = time.getHour();

		if (hour >= open && hour < close) {
			String msg = String.format("Abierto, horario de atencion de %d hasta las %d", open, close);
			request.setAttribute("msg", msg);
			return true;
		}

		response.sendRedirect(request.getContextPath().concat("/closed"));
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		if (handler instanceof HandlerMethod && modelAndView != null) {
			modelAndView.addObject("msg", request.getAttribute("msg"));
		}

	}

}
